package com.roningrum.footballsubmission2.detail.event

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.google.gson.Gson
import com.roningrum.footballsubmission2.*
import com.roningrum.footballsubmission2.R.drawable.ic_add_to_favorites
import com.roningrum.footballsubmission2.R.drawable.ic_added_to_favorites
import com.roningrum.footballsubmission2.R.id.add_to_favorite
import com.roningrum.footballsubmission2.R.menu.detail_menu
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.model.Events
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import okhttp3.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DetailEventActivity : AppCompatActivity(),DetailEventView{

    private lateinit var presenter: DetailEventPresenter
    private lateinit var idEvent: String
    private var idHome: String= ""
    private var idAway: String= ""
    private lateinit var events: Events
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    val client =  OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_detail)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        idHome = intent.getStringExtra("idHome")
        idAway = intent.getStringExtra("idAway")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailEventPresenter(this,request,gson)
        presenter.getDetailMatchList(idEvent)
        swipeRefresh.onRefresh {
            presenter.getDetailMatchList(idEvent)
        }
        val logo = arrayOf(idHome, idAway)
        getBadge(logo)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        val tanggalMatch = SimpleDateFormat("EEE, d MMM yyyy")
            .format(toGMTFormat(data[0].dateEvent, data[0].time))
        val waktuMatch = SimpleDateFormat("HH:mm")
            .format(toGMTFormat(data[0].dateEvent, data[0].time))
        tv_date_detail_event.text = tanggalMatch
        tv_time_detail_event.text = waktuMatch
        tv_home_detail_event.text = data[0].teamHomeName
        tv_away_detail_event.text = data[0].teamAwayName
        if(data[0].teamHomeScore.isNullOrEmpty()&& data[0].teamAwayScore.isNullOrEmpty()){
            tv_skor_detail_event.text = "  VS  "
        } else{
            tv_skor_detail_event.text = data[0].teamHomeScore+"  VS   "+data[0].teamAwayScore
        }
        tv_home_formation_event.text = data[0].strHomeFormation
        tv_away_formation_event.text = data[0].strAwayFormation
        tv_home_goals_event.text = data[0].strHomeGoalDetails?.replace(";","\n")
        tv_away_goals_event.text = data[0].strAwayGoalDetails?.replace(";","\n")
        tv_home_shots_events.text = data[0].intHomeShots
        tv_away_shots_events.text = data[0].intAwayShots
        tv_home_gk_events.text = cleanRapi(data[0].strHomeLineupGoalkeeper)
        tv_away_gk_events.text = cleanRapi(data[0].strAwayLineupGoalkeeper)
        tv_home_def_events.text = cleanRapi(data[0].strHomeLineupDefense)
        tv_away_def_events.text = cleanRapi(data[0].strAwayLineupDefense)
        tv_home_midfield_events.text = cleanRapi(data[0].strHomeLineupMidfield)
        tv_away_midfield_events.text = cleanRapi(data[0].strAwayLineupMidfield)
        tv_home_forward_events.text = cleanRapi(data[0].strHomeLineupForward)
        tv_away_forward_events.text = cleanRapi(data[0].strAwayLineupForward)
    }
    private fun getBadge(logo: Array<String>) {
        for(i in 0..1){
            val request = Request.Builder()
                .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var a = response.body()?.string()
                    runOnUiThread {
                        run(){
                            var json = JSONObject(a)
                            var badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")
                            if(i == 0) {
                                Picasso.get().load(badge).into(img_home_event)
                            }else{
                                Picasso.get().load(badge).into(img_away_event)
                            }
                        }
                    }
                }
            })
        }

    }
    private fun favoriteState() {
        database.use {
            val result = select(Favorit.TABLE_FAVORITE)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to idEvent)
            val favorite = result.parseList(classParser<Favorit>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorit.TABLE_FAVORITE,
                    Favorit.TEAM_ID to events.idEvent,
                    Favorit.TEAM_HOME_NAME to events.teamHomeName,
                    Favorit.TEAM_AWAY_NAME to events.teamAwayName,
                    Favorit.TEAM_HOME_SCORE to events.teamHomeScore,
                    Favorit.TEAM_AWAY_SCORE to events.teamAwayScore,
                    Favorit.DATE_EVENT to events.dateEvent,
                    Favorit.TIME to events.time,
                    Favorit.ID_HOME_TEAM to events.idHomeTeam,
                    Favorit.ID_AWAY_TEAM to events.idAwayTeam,
                    Favorit.STR_HOME_GOAL_DETAIL to  events.strHomeGoalDetails,
                    Favorit.STR_AWAY_GOAL_DETAIL to events.strAwayGoalDetails,
                    Favorit.HOME_SHOTS to events.intHomeShots,
                    Favorit.AWAY_SHOTS to events.intAwayShots,
                    Favorit.HOME_LINEUP_DEFENSE to events.strHomeLineupDefense,
                    Favorit.AWAY_LINEUP_DEFENSE to events.strHomeLineupDefense,
                    Favorit.HOME_LINEUP_MIDFIELD to events.strHomeLineupMidfield,
                    Favorit.AWAY_LINEUP_MIDFIELD to events.strHomeLineupMidfield,
                    Favorit.HOME_LINEUP_FORWARD to events.strHomeLineupForward,
                    Favorit.AWAY_LINEUP_FORWARD to events.strAwayLineupForward,
                    Favorit.HOME_LINEUP_SUBSTITUTES to events.strHomeLineupSubstitutes,
                    Favorit.AWAY_LINEUP_SUBSTITUTES to events.strAwayLineupSubstitutes,
                    Favorit.HOME_LINEUP_FORMASI to events.strHomeFormation,
                    Favorit.AWAY_LINEUP_FORMASI to events.strAwayFormation,
                    Favorit.HOME_LINEUP_GOOLKEEPER to events.strHomeLineupGoalkeeper,
                    Favorit.AWAY_LINEUP_GOOLKEEPER to events.strAwayLineupGoalkeeper)
            }
            snackbar(swipeRefresh,"Addded to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorit.TABLE_FAVORITE, "(TEAM_ID = {id})",
                    "id" to idEvent)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        }catch (e : SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun toGMTFormat(dateEvent: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$dateEvent $time"
        return formatter.parse(dateTime)

    }
    //untuk mengganti semicolon dengan enter
    private fun cleanRapi(player: String?):String?{
        return  player?.replace(";","\n")
    }


}

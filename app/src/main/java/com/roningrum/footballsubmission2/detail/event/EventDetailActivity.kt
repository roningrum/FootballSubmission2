package com.roningrum.footballsubmission2.detail.event

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import com.google.gson.Gson
import com.roningrum.footballsubmission2.R
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.invisible
import com.roningrum.footballsubmission2.model.Events
import com.roningrum.footballsubmission2.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import okhttp3.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity(), DetailEventView {

    private lateinit var presenter: DetailEventPresenter
    private lateinit var idEvent: String
    private var idHome = ""
    private var idAway = ""
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    val client =  OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        supportActionBar?.title = "MatchDetail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progress_bar_detail)
        swipeRefresh = find(R.id.swipe_refresh_detail)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        idHome = intent.getStringExtra("idHome")
        idAway = intent.getStringExtra("idAway")

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

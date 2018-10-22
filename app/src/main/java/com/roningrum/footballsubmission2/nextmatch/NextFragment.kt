package com.roningrum.footballsubmission2.nextmatch


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.roningrum.footballsubmission2.R
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.detail.event.EventDetailActivity
import com.roningrum.footballsubmission2.invisible
import com.roningrum.footballsubmission2.model.Events
import com.roningrum.footballsubmission2.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextFragment : Fragment(), AnkoComponent<Context>, NextView{
    private var events : MutableList<Events> = mutableListOf()
    private lateinit var presenter : NextPresenter
    private lateinit var adapter: NextAdapter
    private lateinit var spinner: Spinner
    private lateinit var listNextEvent : RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName : String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = NextAdapter(events){
            ctx.startActivity<EventDetailActivity>(
                "idEvent" to "${it.idEvent}",
                "idHome" to "${it.idHomeTeam}",
                "idAway" to "${it.idAwayTeam}"
            )
        }
        listNextEvent.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = NextPresenter(this,request,gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                leagueName = spinner.selectedItem.toString()
//                presenter.getNextMatchList(leagueName)
                when(position){
                    0 -> {
                        leagueName = spinner.selectedItem.toString().replace("English Premier League", "4328")
                        presenter.getNextMatchList(leagueName)
                    }
                    1 -> {
                        leagueName = spinner.selectedItem.toString().replace("English League Championship", "4329")
                        presenter.getNextMatchList(leagueName)
                    }
                    2 -> {
                        leagueName = spinner.selectedItem.toString().replace("German Bundesliga", "4331")
                        presenter.getNextMatchList(leagueName)
                    }
                    3 -> {
                        leagueName = spinner.selectedItem.toString().replace("Italian Serie A", "4332")
                        presenter.getNextMatchList(leagueName)
                    }
                    4 -> {
                        leagueName = spinner.selectedItem.toString().replace("French Ligue 1", "4334")
                        presenter.getNextMatchList(leagueName)
                    }
                    5 -> {
                        leagueName = spinner.selectedItem.toString().replace("Spanish La Liga", "4335")
                        presenter.getNextMatchList(leagueName)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        swipeRefresh.onRefresh {
            presenter.getNextMatchList(leagueName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout{
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            rightPadding = dip(16)
            leftPadding = dip(16)

            spinner = spinner{
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_red_light)
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    listNextEvent = recyclerView {
                        id = R.id.listMatch
                        lparams(width= matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                    progressBar = progressBar {  }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()

    }

    override fun showNextMatchList(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()

    }



}

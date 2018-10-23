package com.roningrum.footballsubmission2.prevmatch

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.roningrum.footballsubmission2.R
import com.roningrum.footballsubmission2.model.Events
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

class PrevAdapter(private val events: List<Events>, private val listener: (Events) -> Unit ) : RecyclerView.Adapter<PrevMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevMatchViewHolder {
        return PrevMatchViewHolder(PrevUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: PrevMatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class PrevUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 4f
                }
                verticalLayout {
                    textView("Sabtu, 14 Oktober 2018") {
                        id = R.id.tv_date
                    }.lparams(width = wrapContent, height = wrapContent) {
                        margin = dip(10)
                        gravity = Gravity.CENTER
                    }
                    relativeLayout {
                        textView("Manchester United"){
                            id = R.id.tv_homeTeam
                            textSize = 16f
                            textColor = Color.BLACK
                            gravity = Gravity.END
                        }.lparams(width = matchParent, height = wrapContent) {
                            leftOf(R.id.tv_homeScore)
                            margin = dip(15)
                        }
                        textView("2") {
                            id = R.id.tv_homeScore
                            textSize = 16f
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = wrapContent) {
                            leftOf(R.id.tv_vs)
                            margin = dip(8)
                        }
                        textView("VS") {
                            id = R.id.tv_vs
                            textSize = 16f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent) {
                            centerInParent()
                            margin = dip(8)
                        }
                        textView("0") {
                            id = R.id.tv_awayScore
                            textSize = 16f
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = wrapContent) {
                            rightOf(R.id.tv_vs)
                            margin = dip(8)

                        }
                        textView("LiverPool") {
                            id = R.id.tv_awayTeam
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.START
                        }.lparams(width = matchParent, height = wrapContent){
                           margin = dip(10)
                            rightOf(R.id.tv_awayScore)
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width= matchParent, height = wrapContent){
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}

class PrevMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventDate: TextView = view.find(R.id.tv_date)
    private val eventHomeTeam: TextView = view.find(R.id.tv_homeTeam)
    private val eventAwayTeam: TextView = view.find(R.id.tv_awayTeam)
    private val eventVS: TextView = view.find(R.id.tv_vs)
    private val eventHomeScore: TextView = view.find(R.id.tv_homeScore)
    private val eventAwayScore: TextView = view.find(R.id.tv_awayScore)
    fun bindItem(events: Events, listener:(Events)-> Unit) {
        var tanggalMain = SimpleDateFormat("EEE, d MMM yyyy")
            .format(
                SimpleDateFormat("yyyy-MM-dd")
                    .parse(events.dateEvent)
            )
        eventHomeTeam.text = events.teamHomeName
        eventAwayTeam.text = events.teamAwayName
        eventVS.text = "VS"
        eventHomeScore.text = events.teamHomeScore
        eventAwayScore.text = events.teamAwayScore
        eventDate.text = tanggalMain
        itemView.onClick { listener(events) }
    }

}

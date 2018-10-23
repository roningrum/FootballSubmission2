package com.roningrum.footballsubmission2

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

class FavoriteAdapter(private val events:List<Favorit>, private  val listener: (Favorit) -> Unit) : RecyclerView.Adapter<FavoritViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        return FavoritViewHolder(NextMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }
    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }


    override fun getItemCount(): Int = events.size

}
class NextMatchUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout{
                lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                }
                gravity = R.id.center
                orientation = LinearLayout.VERTICAL
                background = ColorDrawable(Color.parseColor("#ffffff"))

                textView{
                    id = R.id.tv_date
                    textSize = 16f
                    textColor = Color.GREEN
                }.lparams{
                    topMargin= dip(15)
                    gravity = Gravity.CENTER
                }
                relativeLayout{
                    lparams(width= matchParent, height = wrapContent){
                        padding= dip(16)
                    }
                    textView{
                        id = R.id.tv_homeTeam
                        textSize = 18f
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.tv_homeScore)
                    }

                    textView{
                        id = R.id.tv_homeScore
                        textSize = 18f
                        visibility = View.GONE
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginEnd = dip(25)
                        this.leftOf(R.id.tv_vs)
                    }

                    textView{
                        id = R.id.tv_vs
                        textSize= 16f
                    }.lparams{
                        centerHorizontally()
                    }

                    textView{
                        id = R.id.tv_awayScore
                        textSize = 18f
                        visibility = View.GONE
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.tv_vs)
                    }

                    textView{
                        id = R.id.tv_awayTeam
                        textSize = 18f
                    }.lparams{
                        marginStart = dip(25)
                        this.rightOf(R.id.tv_vs)
                    }
                }
            }
        }
    }
}
class FavoritViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvDate : TextView = view.find(R.id.tv_date)
    private val tvVS : TextView = view.find(R.id.tv_vs)
    private val tvhomeTeam : TextView = view.find(R.id.tv_homeTeam)
    private val tvawayTeam : TextView = view.find(R.id.tv_awayTeam)
    private val tvHomeScore : TextView = view.find(R.id.tv_homeScore)
    private val tvAwayScore : TextView = view.find(R.id.tv_awayScore)

    fun bindItem(favorit: Favorit, listener: (Favorit) -> Unit) {
        var tanggalMain = SimpleDateFormat("EEE, d MMM yyyy")
            .format(
                SimpleDateFormat("yyyy-MM-dd")
                    .parse(favorit.dateEvent))
        tvDate.text = tanggalMain
        tvhomeTeam.text = favorit.teamHomeName
        tvawayTeam.text = favorit.teamAwayName
        tvHomeScore.text= favorit.teamHomeScore
        tvAwayScore.text = favorit.teamAwayScore
        tvVS.text = "VS"
        itemView.onClick { listener(favorit) }
    }
}

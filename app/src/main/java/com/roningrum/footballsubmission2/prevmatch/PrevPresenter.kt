package com.roningrum.footballsubmission2.prevmatch

import com.google.gson.Gson
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.api.TheSportDBApi
import com.roningrum.footballsubmission2.model.EventsResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PrevPresenter(private val view: PrevView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson){
    fun getPastMatchList(league:String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevMatch(league)),EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showPrevMatchList(data.events)
            }
        }
    }
}
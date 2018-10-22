package com.roningrum.footballsubmission2.nextmatch

import com.google.gson.Gson
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.api.TheSportDBApi
import com.roningrum.footballsubmission2.model.EventsResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextPresenter(private val view: NextView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson){
    fun getNextMatchList(league:String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(league)),EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showNextMatchList(data.events)
            }
        }
    }
}
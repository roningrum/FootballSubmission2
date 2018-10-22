package com.roningrum.footballsubmission2.detail.event

import com.google.gson.Gson
import com.roningrum.footballsubmission2.api.ApiRepository
import com.roningrum.footballsubmission2.api.TheSportDBApi
import com.roningrum.footballsubmission2.model.EventsResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailEventPresenter(private val view: DetailEventView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){
    fun getDetailMatchList(eventId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailMatch(eventId)), EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showEventDetail(data.events)
            }
        }
    }
}
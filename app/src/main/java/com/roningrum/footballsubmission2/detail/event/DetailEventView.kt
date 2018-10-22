package com.roningrum.footballsubmission2.detail.event

import com.roningrum.footballsubmission2.model.Events

interface DetailEventView{
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<Events>)
}
package com.roningrum.footballsubmission2.prevmatch

import com.roningrum.footballsubmission2.model.Events

interface PrevView{
    fun showLoading()
    fun hideLoading()
    fun showPrevMatchList(data: List<Events>)
}
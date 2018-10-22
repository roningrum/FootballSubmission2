package com.roningrum.footballsubmission2.nextmatch

import com.roningrum.footballsubmission2.model.Events

interface NextView{
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(data: List<Events>)
}
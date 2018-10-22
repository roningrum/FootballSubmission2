package com.roningrum.footballsubmission2.model

import com.google.gson.annotations.SerializedName

data class Events(
    @SerializedName("idEvent")
    val idEvent: String?,

    @SerializedName("strHomeTeam")
    val teamHomeName: String?,

    @SerializedName("strAwayTeam")
    val teamAwayName: String?,

    @SerializedName("intHomeScore")
    val teamHomeScore: String?,

    @SerializedName("intAwayScore")
    val teamAwayScore: String?,

    @SerializedName("dateEvent")
    val dateEvent: String?,

    @SerializedName("strTime")
    val time: String?,

    @SerializedName("idHomeTeam")
    val idHomeTeam: String?,

    @SerializedName("idAwayTeam")
    val idAwayTeam: String?,

    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String?,

    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String?,

    @SerializedName("intHomeShots")
    val intHomeShots: String?,

    @SerializedName("intAwayShots")
    val intAwayShots: String?,

    @SerializedName("strHomeLineupDefense")
    val strHomeLineupDefense: String?,

    @SerializedName("strAwayLineupDefense")
    val strAwayLineupDefense: String?,

    @SerializedName("strHomeLineupMidfield")
    val strHomeLineupMidfield: String?,

    @SerializedName("strAwayLineupMidfield")
    val strAwayLineupMidfield: String?,

    @SerializedName("strHomeLineupForward")
    val strHomeLineupForward: String?,

    @SerializedName("strAwayLineupForward")
    val strAwayLineupForward: String?,

    @SerializedName("strHomeLineupSubstitutes")
    val strHomeLineupSubstitutes: String?,

    @SerializedName("strAwayLineupSubstitutes")
    val strAwayLineupSubstitutes: String?,

    @SerializedName("strHomeFormation")
    val strHomeFormation: String?,

    @SerializedName("strAwayFormation")
    val strAwayFormation: String?,

    @SerializedName("strHomeLineupGoalkeeper")
    val strHomeLineupGoalkeeper: String?,

    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String?
)
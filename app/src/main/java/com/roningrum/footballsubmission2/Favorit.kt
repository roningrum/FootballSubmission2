package com.roningrum.footballsubmission2

data class Favorit(val id: Long?,
                   val teamId: String?,
                   val teamHomeName: String?,
                   val teamAwayName: String?,
                   val teamHomeScore: String?,
                   val teamAwayScore: String?,
                   val dateEvent: String?,
                   val time: String?,
                   val idHomeTeam: String?,
                   val idAwayTeam: String?,
                   val HomeGoalDetail: String?,
                   val AwayGoalDetail: String?,
                   val homeShots: String?,
                   val awayShots: String?,
                   val homeLineUpDefense: String?,
                   val awayLineUpDefense: String?,
                   val awayLineUpMidfield: String?,
                   val homeLineUpMidfield: String?,
                   val homeLineUpForward: String?,
                   val awayLineUpForward: String?,
                   val awayLineUpSubstitutes: String?,
                   val homeLineUpSubstitutes: String?,
                   val homeLineUpFormasi: String?,
                   val awayLineUpFormasi: String?,
                   val homeGoalKeeper : String?,
                   val awayGoalKeeper : String?)

{
    companion object {
        const val TABLE_FAVORITE: String="TABLE_FAVORITE"
        const val ID :String ="ID_"
        const val TEAM_ID : String = "TEAM_ID"
        const val TEAM_HOME_NAME : String= "TEAM_HOME_NAME"
        const val TEAM_AWAY_NAME : String= "TEAM_AWAY_NAME"
        const val TEAM_HOME_SCORE : String= "TEAM_HOME_SCORE"
        const val TEAM_AWAY_SCORE : String= "TEAM_AWAY_SCORE"
        const val DATE_EVENT : String  ="DATE_EVENT"
        const val TIME : String ="TIME"
        const val ID_HOME_TEAM: String  ="ID_HOME_TEAM"
        const val ID_AWAY_TEAM : String = "ID_AWAY_TEAM"
        const val STR_HOME_GOAL_DETAIL : String  ="STR_HOME_GOAL_DETAIL"
        const val STR_AWAY_GOAL_DETAIL : String ="STR_AWAY_GOAL_DETAIL"
        const val HOME_SHOTS: String  ="HOME_SHOTS"
        const val AWAY_SHOTS : String ="AWAY_SHOTS"
        const val HOME_LINEUP_DEFENSE : String ="HOME_LINEUP_DEFENSE"
        const val AWAY_LINEUP_DEFENSE : String ="AWAY_LINEUP_DEFENSE"
        const val HOME_LINEUP_MIDFIELD : String ="HOME_LINEUP_MIDFIELD"
        const val AWAY_LINEUP_MIDFIELD: String ="AWAY_LINEUP_MIDFIELD"
        const val HOME_LINEUP_FORWARD : String ="HOME_LINEUP_FORWARD"
        const val AWAY_LINEUP_FORWARD : String ="AWAY_LINEUP_FORWARD"
        const val HOME_LINEUP_SUBSTITUTES : String ="HOME_LINEUP_SUBSTITUTES"
        const val AWAY_LINEUP_SUBSTITUTES: String  ="AWAY_LINEUP_SUBSTITUTES"
        const val HOME_LINEUP_FORMASI : String ="HOME_LINEUP_FORMASI"
        const val AWAY_LINEUP_FORMASI : String ="AWAY_LINEUP_FORMASI"
        const val HOME_LINEUP_GOOLKEEPER : String ="HOME_LINEUP_GOOLKEEPER"
        const val AWAY_LINEUP_GOOLKEEPER : String ="AWAY_LINEUP_GOOLKEEPER"
    }
}
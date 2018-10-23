package com.roningrum.footballsubmission2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx,"Favorite.db", null,1){
    companion object {
        private var instance : MyDatabaseOpenHelper? =null
        @Synchronized
        fun getInstance(ctx: Context) : MyDatabaseOpenHelper{
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorit.TABLE_FAVORITE, true,
            Favorit.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorit.TEAM_ID to TEXT + UNIQUE,
            Favorit.TEAM_HOME_NAME to TEXT,
            Favorit.TEAM_AWAY_NAME to TEXT,
            Favorit.TEAM_HOME_SCORE to TEXT,
            Favorit.TEAM_AWAY_SCORE to TEXT,
            Favorit.DATE_EVENT to TEXT,
            Favorit.TIME to TEXT,
            Favorit.ID_HOME_TEAM to TEXT,
            Favorit.ID_AWAY_TEAM to TEXT,
            Favorit.STR_HOME_GOAL_DETAIL to  TEXT,
            Favorit.STR_AWAY_GOAL_DETAIL to TEXT,
            Favorit.HOME_SHOTS to TEXT,
            Favorit.AWAY_SHOTS to TEXT,
            Favorit.HOME_LINEUP_DEFENSE to TEXT,
            Favorit.AWAY_LINEUP_DEFENSE to TEXT,
            Favorit.HOME_LINEUP_MIDFIELD to TEXT,
            Favorit.AWAY_LINEUP_MIDFIELD to TEXT,
            Favorit.HOME_LINEUP_FORWARD to TEXT,
            Favorit.AWAY_LINEUP_FORWARD to TEXT,
            Favorit.HOME_LINEUP_SUBSTITUTES to TEXT,
            Favorit.AWAY_LINEUP_SUBSTITUTES to TEXT,
            Favorit.HOME_LINEUP_FORMASI to TEXT,
            Favorit.AWAY_LINEUP_FORMASI to TEXT,
            Favorit.HOME_LINEUP_GOOLKEEPER to TEXT,
            Favorit.AWAY_LINEUP_GOOLKEEPER to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, onlVersion: Int, newVersion: Int) {
        db?.dropTable(Favorit.TABLE_FAVORITE, true)
    }
}
val Context.database: MyDatabaseOpenHelper
    get()= MyDatabaseOpenHelper.getInstance(applicationContext)
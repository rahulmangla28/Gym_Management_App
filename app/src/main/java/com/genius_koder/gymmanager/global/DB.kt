package com.genius_koder.gymmanager.global

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(val context : Context) : SQLiteOpenHelper(context, DB_Name,null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SqlTable.admin)
        db?.execSQL(SqlTable.member)
        db?.execSQL(SqlTable.fee)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion : Int, newVersion : Int) {
        onCreate(db)
    }
// to run the query
    fun executeQuery(sql : String) : Boolean{
        try {
            val database = this.writableDatabase
            database.execSQL(sql)
        }catch (e : Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }
// retrieve data
    fun fireQuery(sql : String) : Cursor? {
        var temCursor : Cursor?=null
        try{
            val database = this.writableDatabase
            temCursor = database.rawQuery(sql,null)

            if(temCursor!=null && temCursor.count>0) {
                temCursor.moveToFirst()
                return temCursor
            }
        }catch (e:Exception) {
            e.printStackTrace()
        }

        return temCursor
    }

    companion object{
        private const val DB_VERSION = 1
        private const val DB_Name = "Gym.DB"
    }

}
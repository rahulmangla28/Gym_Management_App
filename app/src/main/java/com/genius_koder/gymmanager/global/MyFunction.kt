package com.genius_koder.gymmanager.global

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

// function to fetch date according to date format
class MyFunction {

    companion object {

        fun getValue(cursor: Cursor,columnName : String) : String {
            var value : String = ""

            try {
                val col = cursor.getColumnIndex(columnName)
                value = cursor.getString(col)
            }catch (e:Exception) {
                e.printStackTrace()
                Log.d("MyFunction","getValue ${e.printStackTrace()}")
                value=""
            }

            return value
        }

        @SuppressLint("SimpleDateFormat")
        fun returnSQLDateFormat(date : String) : String{
            try {
                if(date.trim().isNotEmpty()) {
                    val dateFormate1 = SimpleDateFormat("dd/MM/yyyy")
                    val firstDate = dateFormate1.parse(date)
                    val dateFormate2 = SimpleDateFormat("yyyy-MM-dd")
                    return dateFormate2.format(firstDate)
                }
            }catch (e:Exception) {
                e.printStackTrace()
            }
            return ""
        }

        @SuppressLint("SimpleDateFormat")
        fun returnUserDateFormat(date : String) : String{
            try {
                if(date.trim().isNotEmpty()) {
                    val dateFormate1 = SimpleDateFormat("yyyy-MM-dd")
                    val firstDate = dateFormate1.parse(date)
                    val dateFormate2 = SimpleDateFormat("dd/MM/yyyy")
                    return dateFormate2.format(firstDate)
                }
            }catch (e:Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun getCurrentData() : String {
            var txtDate = ""
            try {
                txtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            }catch (e : Exception) {
                e.printStackTrace()
            }
            return txtDate
        }

    }

}
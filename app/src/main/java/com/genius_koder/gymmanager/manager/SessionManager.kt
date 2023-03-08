package com.genius_koder.gymmanager.manager

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.widget.Toast

// Session Management is seen in most of the android applications in which
// we can get to know the login and sign-up page.
// used to store the session of the user when user is logged into the application.
class SessionManager (private var _context : Context){

    private var pref : SharedPreferences = _context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private var editor : Editor = pref.edit()

    // shared pref mode
    internal var PRIVATE_MODE = 0

    val isLoggedIn : Boolean
    get() = pref.getBoolean(KEY_IS_LOGGED_IN,false)

    // set the login state of the user
    fun setLogin(isLoggedIn : Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn)
        // commit changes
        editor.commit()

        Toast.makeText(_context,"User Session Login Successfully",Toast.LENGTH_SHORT).show()
    }

    companion object{
        private val TAG = SessionManager::class.java.simpleName
        // shared preferences file name
        private val PREF_NAME = "Login"
        var KEY_USER_ID = "user_id"
        private val KEY_IS_LOGGED_IN = "isLoggedIn"
    }
}
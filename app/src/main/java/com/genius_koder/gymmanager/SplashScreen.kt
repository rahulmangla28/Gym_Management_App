package com.genius_koder.gymmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.genius_koder.gymmanager.activity.HomeActivity
import com.genius_koder.gymmanager.activity.LoginActivity
import com.genius_koder.gymmanager.databinding.ActivityMainBinding
import com.genius_koder.gymmanager.databinding.ActivitySplashScreenBinding
import com.genius_koder.gymmanager.global.DB
import com.genius_koder.gymmanager.manager.SessionManager

class SplashScreen : AppCompatActivity() {

    var db : DB?=null
    var session : SessionManager?=null

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash_screen)

        // inflating layour through view binding
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        session = SessionManager(this)

        // inserts admin data on initialization of app
        insertAdminData()

        // checks whether user is already logged in or not
        if(session?.isLoggedIn==true){
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
                ,5000)
        }else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
                ,5000)
        }

    }

    // checks if admins data is available or not
    // if not then insert or replace it
    private fun insertAdminData() {
        try {
            val sqlCheck = "SELECT * FROM ADMIN"
            db?.fireQuery(sqlCheck)?.use {
                if(it.count>0){
                    Log.d("SplashActivity","data available")
                }else {
                    val sqlQuerry = "INSERT OR REPLACE INTO ADMIN(ID,USER_NAME,PASSWORD,MOBILE)" +
                            "VALUES('1','Admin','123456','8888888888')"
                    db?.executeQuery(sqlQuerry)
                }
            }

        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//    }
}
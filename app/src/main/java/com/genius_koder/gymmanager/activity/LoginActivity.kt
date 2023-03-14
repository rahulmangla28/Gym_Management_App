package com.genius_koder.gymmanager.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.genius_koder.gymmanager.MainActivity
import com.genius_koder.gymmanager.R
import com.genius_koder.gymmanager.databinding.ActivityLoginBinding
import com.genius_koder.gymmanager.databinding.ForgetPasswordDialogBinding
import com.genius_koder.gymmanager.global.DB
import com.genius_koder.gymmanager.global.MyFunction
import com.genius_koder.gymmanager.manager.SessionManager

class LoginActivity : AppCompatActivity() {

    var db : DB? = null
    var session : SessionManager? = null

    var edtUserName : EditText? = null
    var edtPassword : EditText? = null
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflating layout through view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        db = DB(this)
        session = SessionManager(this)
        edtUserName = binding.edtUserName
        edtPassword = binding.edtPassword

        // actions to be performed on click of login button
        binding.btnLogin.setOnClickListener {
            if(validateLogin()) {
                getLogin()
            }
        }

        // actions to be performed on click of forgotPassword button
        binding.forgotPassword.setOnClickListener {
            showDialog()
        }
    }

    // checks whether user is registered in the database or not
    // if user in registered then enables login
    private fun getLogin() {
        try {
            val sqlQuery = "SELECT * FROM ADMIN WHERE USER_NAME = '"+edtUserName?.text.toString().trim()+"' " +
                    "AND PASSWORD = '"+ edtPassword?.text.toString().trim()+"' " +
                    "AND ID = '1'"
            db?.fireQuery(sqlQuery)?.use {
                if(it.count>0) {
                    session?.setLogin(true)
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    session?.setLogin(false)
                    Toast.makeText(this, "Unable to Log In", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    // validate entered login credentials
    private fun validateLogin() : Boolean{
        if(edtUserName?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show()
            return false
        }else if(edtPassword?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // executes when user try to recover the password
    private fun showDialog() {
        val binding2 = ForgetPasswordDialogBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this,R.style.AlertDialogCustom)
        dialog.setContentView(binding2.root)
        dialog.setCancelable(false)
        dialog.show()

        // asking for verification of entered mobile number
        binding2.btnForgetSubmit.setOnClickListener {
            if(binding2.edtForgetMobile.text.toString().trim().isNotEmpty()) {
                checkData(binding2.edtForgetMobile.text.toString().trim(),binding2.txtYourPassword)
            }else {
                Toast.makeText(this,"Enter Mobile Number",Toast.LENGTH_LONG).show()
            }
        }

        binding2.imgBackButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    // checks whether entered mobile number matches the registered mobile number
    // if the mobile number matches then password shows up
    private fun checkData(mobile : String,txtShowPassword : TextView) {
        try {
            val sqlQuery = "SELECT * FROM ADMIN WHERE MOBILE = '$mobile'"
            db?.fireQuery(sqlQuery)?.use {
                if(it.count>0) {
                    val password = MyFunction.getValue(it,"PASSWORD")
                    txtShowPassword.visibility = View.VISIBLE
                    txtShowPassword.text = "Your Password is : $password"
                }else {
                    Toast.makeText(this,"Incorrect Mobile Number",Toast.LENGTH_LONG).show()
                    txtShowPassword.visibility = View.GONE
                }
            }
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

}
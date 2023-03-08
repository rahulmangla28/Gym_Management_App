package com.genius_koder.gymmanager.fragment

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.genius_koder.gymmanager.R
import com.genius_koder.gymmanager.databinding.FragmentChangePasswordBinding
import com.genius_koder.gymmanager.global.DB
import com.genius_koder.gymmanager.global.MyFunction

class FragmentChangePassword : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private var db : DB ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Change Password"
        db = activity?.let { DB(it) }

        // fetches old mobile number
        fillOldMobile()

        // updates or changes new password
        binding.btnChangePassword.setOnClickListener {
            try {
                if(binding.edtNewPassword.text.toString().trim().isNotEmpty()) {
                    if (binding.edtNewPassword.text.toString().trim() == binding.edtConfirmPassword.text.toString().trim()
                    ) {
                        val sqlQuery = "UPDATE ADMIN SET PASSWORD=" + DatabaseUtils.sqlEscapeString(
                            binding.edtNewPassword.text.toString().trim()
                        ) + ""
                        db?.executeQuery(sqlQuery)
                        showToast("Password Changed Successfully")
                    } else {
                        showToast("Password not matched")
                    }
                }else {
                    showToast("Enter new password")
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }

        // updates or changes mobile number
        binding.btnChangeMobile.setOnClickListener {
            try {
                if(binding.edtNewNumber.text.toString().trim().isNotEmpty()) {
                    val sqlQuery =
                        "UPDATE ADMIN SET MOBILE = '" + binding.edtNewNumber.text.toString()
                            .trim() + "'"
                    db?.executeQuery(sqlQuery)
                    showToast("Mobile number changed successfully")
                }else {
                    showToast("Enter new Mobile number")
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    // function to fetch old mobile number details
    private fun fillOldMobile() {
        try {
            val sqlQuery = "SELECT MOBILE FROM ADMIN"
            db?.fireQuery(sqlQuery)?.use {
                val mobile = MyFunction.getValue(it,"MOBILE")
                if(mobile.trim().isNotEmpty()) {
                    binding.edtOldNumber.setText(mobile)
                }
            }
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

    // function to show toast as per the value string
    private fun showToast(value:String) {
        Toast.makeText(activity,value, Toast.LENGTH_LONG).show()
    }

}
package com.genius_koder.gymmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.genius_koder.gymmanager.R
import com.genius_koder.gymmanager.databinding.FragmentAddUpdateFeeBinding
import com.genius_koder.gymmanager.global.DB
import com.genius_koder.gymmanager.global.MyFunction
import com.genius_koder.gymmanager.manager.SessionManager

class FragmentAddUpdateFee : Fragment() {

    private lateinit var binding : FragmentAddUpdateFeeBinding
    var db: DB? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUpdateFeeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Update Fee"
        db = activity?.let { DB(it) }

        binding.btnAddMembership.setOnClickListener {
            if(validate()) {
                saveData()
            }
        }

        fillData()
    }

    // updates membership fees
    private fun validate() :Boolean {

        if(binding.edtOneMonth.text.toString().trim().isEmpty()) {
            showToast("Enter One Month Fee")
            return false
        }else if(binding.edtThreeMonth.text.toString().trim().isEmpty()) {
            showToast("Enter Three Months Fee")
            return false
        }else if(binding.edtSixMonth.text.toString().trim().isEmpty()) {
            showToast("Enter Six Months Fee")
            return false
        }else if(binding.edtOneYear.text.toString().trim().isEmpty()) {
            showToast("Enter One Year Fee")
            return false
        }else if(binding.edtThreeYear.text.toString().trim().isEmpty()) {
            showToast("Enter Three Years Fee")
            return false
        }

        return true
    }

    // saves data in the database as per the changes
    private fun saveData() {
        try {

            val sqlQuery = "INSERT OR REPLACE INTO FEE(ID,ONE_MONTH,THREE_MONTH,SIX_MONTH,ONE_YEAR,THREE_YEAR)VALUES" +
                    "('1','"+binding.edtOneMonth.text.toString().trim()+"','"+binding.edtThreeMonth.text.toString().trim()+"'," +
                    "'"+binding.edtSixMonth.text.toString().trim()+"','"+binding.edtOneYear.text.toString().trim()+"'," +
                    "'"+binding.edtThreeYear.text.toString().trim()+"')"

            db?.executeQuery(sqlQuery)
            showToast("Membership Fee data saved successfully")

        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

    // updates data in the database as per the changes
    private fun fillData() {
        try {

            val sqlQuery = "SELECT * FROM FEE WHERE ID = '1'"
            db?.fireQuery(sqlQuery)?.use {
                if(it.count>0) {
                    it.moveToFirst()
                    binding.edtOneMonth.setText(MyFunction.getValue(it, columnName = "ONE_MONTH"))
                    binding.edtThreeMonth.setText(MyFunction.getValue(it, columnName = "THREE_MONTH"))
                    binding.edtSixMonth.setText(MyFunction.getValue(it, columnName = "SIX_MONTH"))
                    binding.edtOneYear.setText(MyFunction.getValue(it, columnName = "ONE_YEAR"))
                    binding.edtThreeYear.setText(MyFunction.getValue(it, columnName = "THREE_YEAR"))
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    // function to show toast corresponding to value string
    private fun showToast(value:String) {
        Toast.makeText(activity,value,Toast.LENGTH_LONG).show()
    }

}
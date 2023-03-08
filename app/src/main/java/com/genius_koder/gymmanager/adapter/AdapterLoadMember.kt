package com.genius_koder.gymmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.genius_koder.gymmanager.R
import com.genius_koder.gymmanager.databinding.AllMemberListResBinding
import com.genius_koder.gymmanager.model.AllMember
import kotlinx.coroutines.GlobalScope

// adapter class
class AdapterLoadMember(var arrayList: ArrayList<AllMember>) : RecyclerView.Adapter<AdapterLoadMember.MyViewHolder>() {

    private var onCLick : ((String) -> Unit)?= null
    fun onClick(onClick : ((String) -> Unit)) {
        this.onCLick = onClick
    }

    class MyViewHolder(val binding : AllMemberListResBinding) : RecyclerView.ViewHolder(binding.root) {}

    // creates view holder , inflates XML layout and returns view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AllMemberListResBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    // fills up the view holder to display the details
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(arrayList[position]){
                binding.txtAdapterName.text = this.firstName+" "+this.lastName
                binding.txtAdapterAge.text = "Age : "+this.age
                binding.txtAdapterWeight.text = "Weight : "+this.weight
                binding.txtAdapterMobile.text = "Mobile : "+this.mobile
                binding.txtAddress.text = "Address : "+this.address
                binding.txtExpiry.text = "Expiry : "+this.expiryDate

                // set the image as per requirement
                if(this.image.isNotEmpty()) {
                    Glide.with(holder.itemView.context)
                        .load(this.image)
                        .into(binding.imgAdapterPic)
                }else {
                    if(this.gender == "Male"){
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.boy)
                            .into(binding.imgAdapterPic)
                    }else{
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.girl)
                            .into(binding.imgAdapterPic)
                    }
                }

                binding.layoutMemberList.setOnClickListener {
                    onCLick?.invoke(this.id)
                }

            }
        }
    }

    // returns the list size
    override fun getItemCount(): Int {
        return arrayList.size
    }

    // update the list on request
    fun updateList(list : ArrayList<AllMember>) {
        arrayList = list
        notifyDataSetChanged()
    }

}
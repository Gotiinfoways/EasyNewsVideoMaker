package com.easynewsvideomaker.easynewsvideomaker.adapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserModelClass


class UserAdapterClass(
    var edit: (UserModelClass) -> Unit,
    var delete: (String) -> Unit,
    var userBlock: (String, String) -> Unit
) :
    RecyclerView.Adapter<UserAdapterClass.MyViewHolder>() {
    var userList = ArrayList<UserModelClass>()
    var i = 1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.txtIdDisplay)
        var userName: TextView = itemView.findViewById(R.id.txtUserNameDisplay)
        var linEditData: LinearLayout = itemView.findViewById(R.id.linEditData)
        var userBlock: Switch = itemView.findViewById(R.id.switchUserBlock)
        var linDelete: LinearLayout = itemView.findViewById(R.id.linDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.rcv_user_list, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = i++.toString()
        holder.userName.text = userList[position].userName

        holder.linEditData.setOnClickListener {
            edit.invoke(userList[position])

        }
        holder.linDelete.setOnClickListener {
            delete.invoke(userList[position].uid!!)

        }

        holder.userBlock.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Handle the switch state change
            if (isChecked) {
                // Switch is ON
                userBlock.invoke(userList[position].uid!!,"block")

            } else {
                // Switch is OFF
                userBlock.invoke(userList[position].uid!!,"unblock")
            }
        })
    }

    fun updateList(userList: ArrayList<UserModelClass>) {
        this.userList = userList
        notifyDataSetChanged()
    }


}
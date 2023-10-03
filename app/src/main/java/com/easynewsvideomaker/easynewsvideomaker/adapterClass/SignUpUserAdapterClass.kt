package com.easynewsvideomaker.easynewsvideomaker.adapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.modelClass.SignupUserModel

class SignUpUserAdapterClass(var click: (SignupUserModel) -> Unit, var delete: (String) -> Unit) :
    RecyclerView.Adapter<SignUpUserAdapterClass.MyViewHolder>() {
    var signupUserList = ArrayList<SignupUserModel>()
    var i = 1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.txtIdDisplay)
        var userName: TextView = itemView.findViewById(R.id.txtUserNameDisplay)
        var email: TextView = itemView.findViewById(R.id.txtUserEmailDisplay)
        var linClick: LinearLayout = itemView.findViewById(R.id.linClick)
        var linDelete: LinearLayout = itemView.findViewById(R.id.linDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.rcv_signup_user_list, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return signupUserList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = i++.toString()
        holder.userName.text = signupUserList[position].userName
        holder.email.text = signupUserList[position].email

        holder.linClick.setOnClickListener {
            click.invoke(signupUserList[position])

        }
        holder.linDelete.setOnClickListener {
            delete.invoke(signupUserList[position].uid!!)

        }
    }

    fun updateList(signupUserList: ArrayList<SignupUserModel>) {
        this.signupUserList = signupUserList
        notifyDataSetChanged()
    }


}
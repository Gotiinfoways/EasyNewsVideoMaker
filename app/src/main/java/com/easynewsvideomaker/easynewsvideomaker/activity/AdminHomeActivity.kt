package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easynewsvideomaker.easynewsvideomaker.adapterClass.UserAdapterClass
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityAdminHomeBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DeleteDialogBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdminHomeActivity : AppCompatActivity() {
    lateinit var adminHomeBinding: ActivityAdminHomeBinding

    var userList = ArrayList<UserModelClass>()
    lateinit var adapter: UserAdapterClass
    lateinit var mDbRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var progressDialog: Dialog


    // initial count
    private var notification_number_counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adminHomeBinding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(adminHomeBinding.root)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        progressDialog()
        notificationItem()
        initView()
    }

    private fun notificationItem() {

        var itemCount: Int
        mDbRef.child("signup_user")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    itemCount = snapshot.childrenCount.toInt()

                    if (itemCount == 0) {
                        adminHomeBinding.txtNotificationItem.visibility = View.GONE
                    } else if (itemCount != 0) {
                        adminHomeBinding.txtNotificationItem.visibility = View.VISIBLE
                        adminHomeBinding.txtNotificationItem.text = itemCount.toString()
                    }
                    Log.e("TAG", "kkkkk: $itemCount ")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressDialog.setContentView(progressBarBinding.root)

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        progressDialog.show()
    }

    private fun initView() {


        adminHomeBinding.imgNotification.setOnClickListener {
            var i = Intent(this, NotificationActivity::class.java)
            startActivity(i)
        }
        adminHomeBinding.cdCreateAccountBtn.setOnClickListener {
            var i = Intent(this, CreateAccountActivity::class.java)
            startActivity(i)
        }
        adapter = UserAdapterClass({
            var i = Intent(this, CreateAccountActivity::class.java)
            i.putExtra("id", it.uid)
            i.putExtra("userName", it.userName)
            i.putExtra("mobilNumber", it.mobilNumber)
            i.putExtra("email", it.email)
            i.putExtra("password", it.password)
            i.putExtra("packageType", it.packageType)
            i.putExtra("buttonName", "Update")
            i.putExtra("updateData", true)
            startActivity(i)

        }, { uid ->
            deleteRecordFromDatabase(uid)
        })
        var manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adminHomeBinding.rcvView.layoutManager = manger
        adminHomeBinding.rcvView.adapter = adapter

        mDbRef.child("user")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (i in snapshot.children) {
                        var data = i.getValue(UserModelClass::class.java)
                        Log.e("TAG", "onDataChange: " + data?.userName + data?.email)
                        data?.let { it1 -> userList.add(it1) }
                    }
                    adapter.updateList(userList)
                    progressDialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun deleteRecordFromDatabase(uid: String) {

        var deleteDialog = Dialog(this)

        var dialogBinding = DeleteDialogBinding.inflate(layoutInflater)
        deleteDialog.setContentView(dialogBinding.root)

        dialogBinding.btnCanselDelete.setOnClickListener {
            deleteDialog.dismiss()
            Toast.makeText(this, "Cansel", Toast.LENGTH_SHORT).show()
        }
        dialogBinding.btnDelete.setOnClickListener {
            mDbRef.child("user").child(uid).removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_SHORT)
                            .show()
                        adapter.updateList(userList)
                    }
                }.addOnFailureListener {
                    Log.e("TAG", "initView: " + it.message)
                }

            deleteDialog.dismiss()
        }

        deleteDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
        deleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        deleteDialog.show()

    }
}
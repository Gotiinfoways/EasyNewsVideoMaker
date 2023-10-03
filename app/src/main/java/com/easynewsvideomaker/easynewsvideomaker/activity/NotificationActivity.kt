package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.adapterClass.SignUpUserAdapterClass
import com.easynewsvideomaker.easynewsvideomaker.modelClass.SignupUserModel
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityNotificationBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DeleteDialogBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.SignupApproveDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationActivity : AppCompatActivity() {
    lateinit var notificationBinding: ActivityNotificationBinding
    var signupUserList = ArrayList<SignupUserModel>()
    lateinit var adapter: SignUpUserAdapterClass
    lateinit var mDbRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        progressDialog()
        initView()
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
        notificationBinding.imgBack.setOnClickListener {
            onBackPressed()
        }

        adapter = SignUpUserAdapterClass({

            approveDialog(it)

        }, { uid ->
            deleteRecordFromDatabase(uid)
        })
        var manger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        notificationBinding.rcvNotificationView.layoutManager = manger
        notificationBinding.rcvNotificationView.adapter = adapter

        mDbRef.child("signup_user")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    signupUserList.clear()
                    for (i in snapshot.children) {
                        var data = i.getValue(SignupUserModel::class.java)
                        Log.e("TAG", "onDataChange: " + data?.userName + data?.email)
                        data?.let { it1 -> signupUserList.add(it1) }
                    }
                    adapter.updateList(signupUserList)
                    progressDialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun approveDialog(signupUserModel: SignupUserModel) {
        var approveDialog = Dialog(this)
        var approveDialogBinding = SignupApproveDialogBinding.inflate(layoutInflater)
        approveDialog.setContentView(approveDialogBinding.root)

        approveDialogBinding.txtUserNameDialog.text = signupUserModel.userName
        approveDialogBinding.txtChannelNameDialog.text = signupUserModel.channelName
        approveDialogBinding.txtPhoneNumberDialog.text = signupUserModel.mobilNumber
        approveDialogBinding.txtEmailDialog.text = signupUserModel.email


        approveDialogBinding.imgClose.setOnClickListener {

            approveDialog.dismiss()
        }
        approveDialogBinding.btnApprove.setOnClickListener {

            approveDialogBinding.btnApprove.setBackgroundResource(R.drawable.button_red_round)
            approveDialogBinding.btnDisapprove.setBackgroundResource(R.drawable.button_black_round)

            var i = Intent(this, CreateAccountActivity::class.java)
            i.putExtra("id", signupUserModel.uid)
            i.putExtra("userName", signupUserModel.userName)
            i.putExtra("channelName", signupUserModel.channelName)
            i.putExtra("mobilNumber", signupUserModel.mobilNumber)
            i.putExtra("email", signupUserModel.email)
            i.putExtra("password", signupUserModel.password)
            i.putExtra("buttonName", "New Data Insert")
            i.putExtra("signupDataInsert", true)
            startActivity(i)

            approveDialog.dismiss()
        }
        approveDialogBinding.btnDisapprove.setOnClickListener {

            approveDialogBinding.btnDisapprove.setBackgroundResource(R.drawable.button_red_round)
            approveDialogBinding.btnApprove.setBackgroundResource(R.drawable.button_black_round)

            approveDialog.dismiss()
        }


        approveDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        approveDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        approveDialog.show()
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
            mDbRef.child("signup_user").child(uid).removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_SHORT)
                            .show()
                        adapter.updateList(signupUserList)
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
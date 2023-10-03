package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityAdminLoginBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdminLoginActivity : AppCompatActivity() {
    lateinit var adminLoginBinding: ActivityAdminLoginBinding

    lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adminLoginBinding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(adminLoginBinding.root)
        auth = Firebase.auth

//        layoutBackgroundChange()
        progressDialog()
        initView()
    }

    private fun layoutBackgroundChange() {
        adminLoginBinding.linEmail.setOnClickListener {
            adminLoginBinding.linEmail.setBackgroundResource(R.drawable.card_corner)
            adminLoginBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        adminLoginBinding.linPassword.setOnClickListener {
            adminLoginBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            adminLoginBinding.linPassword.setBackgroundResource(R.drawable.card_corner)
        }

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

    }

    private fun initView() {
        //status bar color change
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.BLACK


        // Hide the password
        adminLoginBinding.edtPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        adminLoginBinding.imgPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                adminLoginBinding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                adminLoginBinding.imgPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                adminLoginBinding.edtPassword.transformationMethod = null
                isPasswordVisible = true
                adminLoginBinding.imgPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            adminLoginBinding.edtPassword.setSelection(adminLoginBinding.edtPassword.text.length)
        }
        adminLoginBinding.cdLoginBtn.setOnClickListener {

            var email = adminLoginBinding.edtEmail.text.toString()
            var password = adminLoginBinding.edtPassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "email value is empty. please fill email ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this,
                    "password value is empty. please fill password ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                progressDialog.show()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (email == "admin@gmail.com") {
                            progressDialog.dismiss()
                            Toast.makeText(this, "Admin Login Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, AdminHomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(this, "You are not Admin", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }
    }
}
package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.SignUpActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityLoginScreenBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class LoginScreenActivity : AppCompatActivity() {
    private lateinit var loginScreenBinding: ActivityLoginScreenBinding

    private lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var isPasswordVisible = false
//    private var android_id: String? = null

    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginScreenBinding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(loginScreenBinding.root)
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
//        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
        progressDialog()
        initView()
    }

    private fun progressDialog() {
        dialog = Dialog(this)
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        dialog.setContentView(progressBarBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initView() {

        //status bar color change
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.BLACK


        var sharedPreferences = getSharedPreferences("MySharePref", AppCompatActivity.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLogin", false) == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

        // Hide the password
        loginScreenBinding.edtPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        loginScreenBinding.imgPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                loginScreenBinding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                loginScreenBinding.imgPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                loginScreenBinding.edtPassword.transformationMethod = null
                isPasswordVisible = true
                loginScreenBinding.imgPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            loginScreenBinding.edtPassword.setSelection(loginScreenBinding.edtPassword.text.length)
        }

        loginScreenBinding.txtAdmin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        })

        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid
        loginScreenBinding.cdLoginBtn.setOnClickListener {


            var email = loginScreenBinding.edtEmail.text.toString()
            var password = loginScreenBinding.edtPassword.text.toString()

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
                dialog.show()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Storing the device token when a user logs in
                        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                        val currentDeviceToken = "This Email is login"




// Checking if the device token matches the stored token
                        mDbRef.child("user").child(currentUserId!!).child("device_token")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {


//                                    var storedDeviceToken = dataSnapshot.child("device_token").value.toString()
                                    val storedDeviceToken = dataSnapshot.getValue(String::class.java)
                                    if (currentDeviceToken == storedDeviceToken) {
                                        // Device token matches, allow login
                                        dialog.dismiss()
                                        Toast.makeText(this@LoginScreenActivity, "this email is login other device", Toast.LENGTH_SHORT).show()

                                    } else {

                                        // Device token does not match, handle accordingly

                                        mDbRef.child("user").child(currentUserId!!).child("device_token")
                                            .setValue(currentDeviceToken)

                                        val intent = Intent(this@LoginScreenActivity, HomeActivity::class.java)
                                        startActivity(intent)
                                        finish()

                                        var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
                                        myEdit.putBoolean("isLogin", true)
                                        myEdit.putString("email", email)

                                        myEdit.commit()
                                        Toast.makeText(this@LoginScreenActivity, "Login Success", Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()

                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Handle database errors
                                }
                            })

//                        oneDeviceLogin(currentUserId)
                    }
                }.addOnFailureListener {
                    dialog.dismiss()
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        loginScreenBinding.linSignUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })

        loginScreenBinding.gotiinfoways.setOnClickListener(View.OnClickListener {
            val url = "https://www.gotiinfoways.com/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        })

    }

//    private fun oneDeviceLogin(currentUserId: String?) {
//        if (currentUserId != null) {
//            val databaseRef =
//                FirebaseDatabase.getInstance().getReference("users").child(currentUserId)
//            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val storedDeviceToken =
//                        dataSnapshot.child("device_token").getValue(String::class.java)
//                    val currentDeviceToken = android_id
//
//                    if (currentDeviceToken == storedDeviceToken) {
//                        // Allow login
//                    } else {
//                        // Notify the user that they are already logged in on another device
//                        // You can implement a log-out mechanism here if needed
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Handle database errors
//                }
//            })
//        }
//    }
}
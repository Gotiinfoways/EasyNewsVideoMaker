package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.modelClass.SignupUserModel
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivitySignUpBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    lateinit var signUpBinding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false
    private lateinit var mDbRef: DatabaseReference


    var userId: String? = null   //  id variable  define
    var flag = 0    //  flag variable  define



    lateinit var progressDialog: Dialog

    lateinit var android_id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Initialize Firebase Auth
        auth = Firebase.auth

        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)


        //status bar color change
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.BLACK

//        layoutBackgroundChange()
        progressDialog()
        passwordToggleVisible()
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
    }

    private fun passwordToggleVisible() {
        // Hide the password
        signUpBinding.edtPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        signUpBinding.imgPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                signUpBinding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                signUpBinding.imgPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                signUpBinding.edtPassword.transformationMethod = null
                isPasswordVisible = true
                signUpBinding.imgPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            signUpBinding.edtPassword.setSelection(signUpBinding.edtPassword.text.length)
        }

        // Hide the confirm password
        signUpBinding.edtConfirmPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        signUpBinding.imgCoPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                signUpBinding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                signUpBinding.imgCoPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                signUpBinding.edtConfirmPassword.transformationMethod = null
                isPasswordVisible = true
                signUpBinding.imgCoPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            signUpBinding.edtConfirmPassword.setSelection(signUpBinding.edtConfirmPassword.text.length)
        }
    }

    private fun initView() {


        var sharedPreferences = getSharedPreferences("MySharePref", AppCompatActivity.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLogin", false) == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }


        signUpBinding.cdSignUpBtn.setOnClickListener {
            var userName = signUpBinding.edtUserName.text.toString()
            var mobilNumber = signUpBinding.edtMobilNumber.text.toString()
            var email = signUpBinding.edtEmail.text.toString()
            var password = signUpBinding.edtPassword.text.toString()
            var confPassword = signUpBinding.edtConfirmPassword.text.toString()
//            var login_device_name = Build.MODEL
            var login_device_name =  android_id

            if (userName.isEmpty()) {
                Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show()
            } else if (mobilNumber.isEmpty()) {
                Toast.makeText(this, "Please Enter Mobil Number", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show()

            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()

            } else if (password.length < 8) {
                Toast.makeText(this, "Minimum 8 Character Password", Toast.LENGTH_SHORT).show()

            } else if (!password.matches(".*[A-Z].*".toRegex())) {

                Toast.makeText(this, "Must Contain 1 Upper-case Character", Toast.LENGTH_SHORT)
                    .show()

            } else if (!password.matches(".*[a-z].*".toRegex())) {
                Toast.makeText(this, "Must Contain 1 Lower-case Character", Toast.LENGTH_SHORT)
                    .show()

            } else if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
                Toast.makeText(
                    this,
                    "Must Contain 1 Special Character (@#\$%^&+=)",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (confPassword.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please Enter Confirm Password",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (!confPassword.equals(password)) {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()

            } else {
                progressDialog.show()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            addUserToDatabase(
                                userName,
                                mobilNumber,
                                email,
                                password,
                                login_device_name,
                                auth.currentUser?.uid!!
                            )


                            var myEdit: SharedPreferences.Editor =
                                sharedPreferences.edit()
                            myEdit.putBoolean("isLogin", true)
                            myEdit.putString("email", email)

                            myEdit.commit()


                            Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT)
                                .show()

                            progressDialog.dismiss()

                            var i = Intent(this, HomeActivity::class.java)
                            i.putExtra("signupPage", "signup_success")
                            startActivity(i)
                            finish()
                        }
                    }.addOnFailureListener {
                        Log.e("TAG", "SignUp:Fail  " + it.message)
                        Toast.makeText(this, "SignUp Fail", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }

            }

        }


    }


    private fun addUserToDatabase(
        userName: String,
        mobilNumber: String,
        email: String,
        password: String,
        login_device_name: String,
        uid: String,
    ) {


        mDbRef.child("signup_user").child(uid).setValue(
            SignupUserModel(
                userName,
                mobilNumber,
                email,
                password,
                login_device_name,
                uid
            )
        )


    }

    private fun layoutBackgroundChange() {

        signUpBinding.linUserName.setOnClickListener {
            signUpBinding.linUserName.setBackgroundResource(R.drawable.card_corner)
            signUpBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        signUpBinding.linMobileNumber.setOnClickListener {
            signUpBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linMobileNumber.setBackgroundResource(R.drawable.card_corner)
            signUpBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }
        signUpBinding.linEmail.setOnClickListener {
            signUpBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linEmail.setBackgroundResource(R.drawable.card_corner)
            signUpBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        signUpBinding.linPassword.setOnClickListener {
            signUpBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linPassword.setBackgroundResource(R.drawable.card_corner)
            signUpBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        signUpBinding.linConPassword.setOnClickListener {
            signUpBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            signUpBinding.linConPassword.setBackgroundResource(R.drawable.card_corner)
        }


    }
}

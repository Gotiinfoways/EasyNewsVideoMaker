package com.easynewsvideomaker.easynewsvideomaker.activity


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserModelClass
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityCreateAccountBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CreateAccountActivity : AppCompatActivity() {
    lateinit var createAccountBinding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    var packageType: String? = null
    private var isPasswordVisible = false
    private lateinit var mDbRef: DatabaseReference

    var userId: String? = null   //  id variable  define
    var flag = 0    //  flag variable  define
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAccountBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(createAccountBinding.root)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Initialize Firebase Auth
        auth = Firebase.auth


        if (intent != null && intent.hasExtra("updateData")) {  // data update key access this class

            flag = 1
            var newUserName: String? = intent.getStringExtra("userName")   // key set in variable
            var newChannelName: String? =
                intent.getStringExtra("channelName")   // key set  variable
            var newMobilNumber: String? =
                intent.getStringExtra("mobilNumber")   // key set  variable
            var newEmail: String? = intent.getStringExtra("email")   // key set  variable
            var newPassword: String? = intent.getStringExtra("password")   // key set  variable
            var newPackageType: String? =
                intent.getStringExtra("packageType")   // key set  variable
            var newButtonName: String? = intent.getStringExtra("buttonName")   // key set  variable
            userId = intent.getStringExtra("id")   // key set  variable

            Log.e("TAG", "RS_ID: " + userId)
            Log.e("TAG", "RS_Amount: " + newUserName)

            createAccountBinding.edtUserName.setText(newUserName)  //variable set in text view
            createAccountBinding.edtChannelName.setText(newChannelName)  //variable set in text view
            createAccountBinding.edtMobilNumber.setText(newMobilNumber)  //variable set in text view
            createAccountBinding.edtEmail.setText(newEmail)  //variable set in text view
            createAccountBinding.edtPassword.setText(newPassword)  //variable set in text view
//            createAccountBinding.rgPackage.s(newAmt)  //variable set in text view
            createAccountBinding.btnSubmitText.text = newButtonName  //variable set in text view

            createAccountBinding.txtPageTitle.text = "update Data"  //change page title
        }

        if (intent != null && intent.hasExtra("signupDataInsert")) {  // data update key access this class

            flag = 2
            var newUserName: String? = intent.getStringExtra("userName")   // key set in variable
            var newChannelName: String? =
                intent.getStringExtra("channelName")   // key set  variable
            var newMobilNumber: String? =
                intent.getStringExtra("mobilNumber")   // key set  variable
            var newEmail: String? = intent.getStringExtra("email")   // key set  variable
            var newPassword: String? = intent.getStringExtra("password")   // key set  variable
            var newButtonName: String? = intent.getStringExtra("buttonName")   // key set  variable
            userId = intent.getStringExtra("id")   // key set  variable

            Log.e("TAG", "RS_ID: " + userId)
            Log.e("TAG", "RS_Amount: " + newUserName)

            createAccountBinding.edtUserName.setText(newUserName)  //variable set in text view
            createAccountBinding.edtChannelName.setText(newChannelName)  //variable set in text view
            createAccountBinding.edtMobilNumber.setText(newMobilNumber)  //variable set in text view
            createAccountBinding.edtEmail.setText(newEmail)  //variable set in text view
            createAccountBinding.edtPassword.setText(newPassword)  //variable set in text view
            createAccountBinding.btnSubmitText.text = newButtonName  //variable set in text view

        }


//        layoutBackgroundChange()
        progressDialog()
        dateAndPackage()
        passwordToggleVisible()
        initView()

    }

    private fun layoutBackgroundChange() {

        createAccountBinding.linUserName.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }
        createAccountBinding.linChannel.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        createAccountBinding.linMobileNumber.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }
        createAccountBinding.linEmail.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        createAccountBinding.linPassword.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
        }

        createAccountBinding.linConPassword.setOnClickListener {
            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_corner)
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

    private fun passwordToggleVisible() {
        // Hide the password
        createAccountBinding.edtPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        createAccountBinding.imgPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                createAccountBinding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                createAccountBinding.imgPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                createAccountBinding.edtPassword.transformationMethod = null
                isPasswordVisible = true
                createAccountBinding.imgPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            createAccountBinding.edtPassword.setSelection(createAccountBinding.edtPassword.text.length)
        }

        // Hide the confirm password
        createAccountBinding.edtConfirmPassword.transformationMethod =
            PasswordTransformationMethod.getInstance()
        createAccountBinding.imgCoPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                // Hide the password
                createAccountBinding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordVisible = false
                createAccountBinding.imgCoPasswordToggle.setImageResource(R.drawable.eye_hidde)
            } else {
                // Show the password
                createAccountBinding.edtConfirmPassword.transformationMethod = null
                isPasswordVisible = true
                createAccountBinding.imgCoPasswordToggle.setImageResource(R.drawable.eye_show)
            }

            // Move the cursor to the end of the text
            createAccountBinding.edtConfirmPassword.setSelection(createAccountBinding.edtConfirmPassword.text.length)
        }
    }

    private fun dateAndPackage() {
        createAccountBinding.imgBack.setOnClickListener {
            onBackPressed()
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Get the current date
        val currentDate = Calendar.getInstance()

        // Format and set the current date in the first TextView
        val formattedCurrentDate = dateFormat.format(currentDate.time)
        createAccountBinding.txtStartDate.text = formattedCurrentDate

        // Add 30 days to the current date
        currentDate.add(Calendar.DAY_OF_MONTH, 30)

        // Format and set the future date in the second TextView
        val formattedFutureDate = dateFormat.format(currentDate.time)
        createAccountBinding.txtEndDate.text = formattedFutureDate

        packageType = "Golden"
        if (packageType == "Golden") {  // variable set in if statement
            createAccountBinding.rbGolden.isChecked = true// set radio button Golden true
        }


    }

    private fun initView() {

        createAccountBinding.cdSubmitBtn.setOnClickListener {
            var userName = createAccountBinding.edtUserName.text.toString()
            var channelName = createAccountBinding.edtChannelName.text.toString()
            var mobilNumber = createAccountBinding.edtMobilNumber.text.toString()
            var email = createAccountBinding.edtEmail.text.toString()
            var password = createAccountBinding.edtPassword.text.toString()
            var confPassword = createAccountBinding.edtConfirmPassword.text.toString()
            var startDate = createAccountBinding.txtStartDate.text.toString()
            var endDate = createAccountBinding.txtEndDate.text.toString()

            if (createAccountBinding.rgPackage.checkedRadioButtonId == -1) {

            } else {
                val selectId: Int = createAccountBinding.rgPackage.checkedRadioButtonId
                var selectedRadioButton: RadioButton = findViewById(selectId)
                var text = selectedRadioButton.text.toString()

                packageType = if (text == "Golden") {
                    "Golden"
                } else {
                    "Silver"
                }

            }

            if (userName.isEmpty()) {
                Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show()
            } else if (channelName.isEmpty()) {
                Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_SHORT).show()
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
                if (flag == 1) {
                    mDbRef.child("user").child(userId!!).setValue(
                        UserModelClass(
                            userName,
                            channelName,
                            mobilNumber,
                            email,
                            password,
                            startDate,
                            endDate,
                            packageType!!,
                            userId!!
                        )
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Record Updated Successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                progressDialog.dismiss()
                                var i = Intent(this, AdminHomeActivity::class.java)
                                startActivity(i)
                            }
                        }.addOnFailureListener {
                            Log.e("TAG", "initView: " + it.message)
                            progressDialog.dismiss()
                        }


                }
                else if (flag == 2) {
                    mDbRef.child("user").child(userId!!).setValue(
                        UserModelClass(
                            userName,
                            channelName,
                            mobilNumber,
                            email,
                            password,
                            startDate,
                            endDate,
                            packageType!!,
                            userId!!
                        )
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Record Updated Successfully",
                                    Toast.LENGTH_SHORT

                                )
                                    .show()

                                deleteSignUpUserData(userId!!)
                                progressDialog.dismiss()
                                var i = Intent(this, AdminHomeActivity::class.java)
                                startActivity(i)
                            }
                        }.addOnFailureListener {
                            Log.e("TAG", "initView: " + it.message)
                            progressDialog.dismiss()
                        }
                }
                else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information

                                addUserToDatabase(
                                    userName,
                                    channelName,
                                    mobilNumber,
                                    email,
                                    password,
                                    startDate,
                                    endDate,
                                    packageType,
                                    auth.currentUser?.uid!!
                                )
                                Toast.makeText(this, "Account Create Success", Toast.LENGTH_SHORT)
                                    .show()

                                progressDialog.dismiss()

                                var i = Intent(this, AdminHomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }.addOnFailureListener {
                            Log.e("TAG", "createUserWithEmail:Fail  " + it.message)
                            Toast.makeText(this, "Account Create Fail", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                }
            }

        }


    }

    private fun deleteSignUpUserData(userId: String) {

        mDbRef.child("signup_user").child(userId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                }
            }.addOnFailureListener {
                Log.e("TAG", "initView: " + it.message)
            }

    }

    private fun addUserToDatabase(
        userName: String,
        channelName: String,
        mobilNumber: String,
        email: String,
        password: String,
        startDate: String,
        endDate: String,
        packageType: String?,
        uid: String,
    ) {


        mDbRef.child("user").child(uid).setValue(
            UserModelClass(
                userName,
                channelName,
                mobilNumber,
                email,
                password,
                startDate,
                endDate,
                packageType!!,
                uid
            )
        )


    }
}
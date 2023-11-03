package com.easynewsvideomaker.easynewsvideomaker.activity


import android.app.DatePickerDialog
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.xml.datatype.DatatypeConstants.MONTHS


class CreateAccountActivity : AppCompatActivity() {
    lateinit var createAccountBinding: ActivityCreateAccountBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    private var isPasswordVisible = false

    var userId: String? = null   //  id variable  define
    var flag = 0    //  flag variable  define
    lateinit var progressDialog: Dialog
    var userName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var packageType: String? = null
    var loginDeviceName: String? = null
    var channelLogo: String? = null
    var channelName: String? = null
    var repoterName: String? = null
    var facebookLink: String? = null
    var twitterLink: String? = null
    var instagramLink: String? = null
    var youtubeLink: String? = null
    var websiteLink: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAccountBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(createAccountBinding.root)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Initialize Firebase Auth
        auth = Firebase.auth


        if (intent != null && intent.hasExtra("updateData")) {  // data update key access this class

            flag = 1
            var newButtonName: String? = intent.getStringExtra("buttonName")   // key set  variable
            userId = intent.getStringExtra("id")   // key set  variable

            Log.e("TAG", "RS_ID: " + userId)


            // Attach a ValueEventListener to retrieve user data
            mDbRef.child("user").child(userId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(UserModelClass::class.java)
                            if (user != null) {
                                // User data retrieved successfully
                                val uid = user.uid
                                val username = user.userName
                                val mobileNumber = user.mobilNumber
                                val email = user.email
                                val password = user.password
                                startDate = user.startDate
                                endDate = user.endDate
                                packageType = user.packageType
                                loginDeviceName = user.login_device_name
                                channelLogo = user.channelLogo!!
                                channelName = user.channelName!!
                                repoterName = user.repoterName!!
                                facebookLink = user.facebookLink!!
                                twitterLink = user.twitterLink!!
                                instagramLink = user.instagramLink!!
                                youtubeLink = user.youtubeLink!!
                                websiteLink = user.websiteLink!!
                                // Handle the user data as needed

                                createAccountBinding.edtUserName.setText(username)
                                createAccountBinding.edtMobilNumber.setText(mobileNumber)  //variable set in text view
                                createAccountBinding.edtEmail.setText(email)  //variable set in text view
                                createAccountBinding.edtPassword.setText(password)  //variable set in text view
                                createAccountBinding.edtConfirmPassword.setText(password)  //variable set in text view
                                createAccountBinding.txtStartDate.setText(startDate)  //variable set in text view
                                createAccountBinding.txtEndDate.setText(endDate)  //variable set in text view


                                if (packageType == "Golden") {
                                    "Golden"
                                    createAccountBinding.rbGolden.isChecked = true
                                } else {
                                    "Silver"

                                    createAccountBinding.rbSilver.isChecked = true
                                }
                                createAccountBinding.txtLoginDeviceName.setText(loginDeviceName)  //variable set in text view
                                createAccountBinding.btnSubmitText.text =
                                    newButtonName  //variable set in text view
                            }
                        } else {
                            // User data does not exist
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle any errors, such as permission issues or network problems
                    }
                })


//            createAccountBinding.rgPackage.s(newAmt)  //variable set in text view
            createAccountBinding.btnSubmitText.text = newButtonName  //variable set in text view

            createAccountBinding.txtPageTitle.text = "update Data"  //change page title
        }

        if (intent != null && intent.hasExtra("signupDataInsert")) {  // data update key access this class

            flag = 2
            var newButtonName: String? = intent.getStringExtra("buttonName")   // key set  variable
            userId = intent.getStringExtra("uid")   // key set  variable

            Log.e("TAG", "RS_ID: " + userId)

            // Attach a ValueEventListener to retrieve user data
            mDbRef.child("signup_user").child(userId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(UserModelClass::class.java)
                            if (user != null) {
                                // User data retrieved successfully
                                val uid = user.uid
                                val username = user.userName
                                val mobileNumber = user.mobilNumber
                                val email = user.email
                                val password = user.password
                                val login_device_name = user.login_device_name
                                channelLogo = user.channelLogo!!
                                channelName = user.channelName!!
                                repoterName = user.repoterName!!
                                facebookLink = user.facebookLink!!
                                twitterLink = user.twitterLink!!
                                instagramLink = user.instagramLink!!
                                youtubeLink = user.youtubeLink!!
                                websiteLink = user.websiteLink!!
                                // Handle the user data as needed

                                createAccountBinding.edtUserName.setText(username)
                                createAccountBinding.edtMobilNumber.setText(mobileNumber)  //variable set in text view
                                createAccountBinding.edtEmail.setText(email)  //variable set in text view
                                createAccountBinding.edtPassword.setText(password)  //variable set in text view
                                createAccountBinding.edtConfirmPassword.setText(password)  //variable set in text view
                                createAccountBinding.txtLoginDeviceName.setText(login_device_name)  //variable set in text view
                                createAccountBinding.btnSubmitText.text =
                                    newButtonName  //variable set in text view
                            }
                        } else {
                            // User data does not exist
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle any errors, such as permission issues or network problems
                    }
                })


        }

        createAccountBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
//        layoutBackgroundChange()
        progressDialog()
        dateAndPackage()
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
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // date piker
        createAccountBinding.txtStartDate.setOnClickListener {
//            // on below line we are getting
//            // the instance of our calendar.
//            val c = Calendar.getInstance()
//
//            // on below line we are getting
//            // our day, month and year.
//            val year = c.get(Calendar.YEAR)
//            val month = c.get(Calendar.MONTH)
//            val day = c.get(Calendar.DAY_OF_MONTH)
//
//            // on below line we are creating a
//            // variable for date picker dialog.
//            val datePickerDialog = DatePickerDialog(
//                // on below line we are passing context.
//                this,
//                { view, year, monthOfYear, dayOfMonth ->
//                    // on below line we are setting
//                    // date to our edit text.
//                    val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
//                    createAccountBinding.txtStartDate.text = dat
//
//
//                },
//                // on below line we are passing year, month
//                // and day for the selected date in our date picker.
//                year,
//                month,
//                day
//            )
//            // Add 30 days to the current date
//            c.add(Calendar.DAY_OF_MONTH, 30)
//
//            // Format and set the future date in the second TextView
//            val formattedFutureDate = dateFormat.format(c.time)
//            createAccountBinding.txtEndDate.text = formattedFutureDate
//            // at last we are calling show
//            // to display our date picker dialog.
//            datePickerDialog.show()



            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    // The user has selected a date
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth)
                    val dat = (dayOfMonth.toString() + "/" + (month + 1) + "/" + year)
                    createAccountBinding.txtStartDate.text = dat

                    // Add 30 days to the selected date
                    selectedCalendar.add(Calendar.DAY_OF_MONTH, 30)

                    // Format the new date as a string (you can use any desired format)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    val resultDate = dateFormat.format(selectedCalendar.time)

                    // Display the result
                    createAccountBinding.txtEndDate.text = resultDate
                },
                currentYear,
                currentMonth,
                currentDay
            )

            datePickerDialog.show()


        }



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

//        packageType = "Golden"
        if (packageType == null) {  // variable set in if statement
            createAccountBinding.rbGolden.isChecked = true// set radio button Golden true
        }


    }

    private fun initView() {


        createAccountBinding.cdSubmitBtn.setOnClickListener {
            userName = createAccountBinding.edtUserName.text.toString()
            mobilNumber = createAccountBinding.edtMobilNumber.text.toString()
            email = createAccountBinding.edtEmail.text.toString()
            password = createAccountBinding.edtPassword.text.toString()
            var confPassword = createAccountBinding.edtConfirmPassword.text.toString()
            startDate = createAccountBinding.txtStartDate.text.toString()
            endDate = createAccountBinding.txtEndDate.text.toString()


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

            if (userName!!.isEmpty()) {
                Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show()
            } else if (mobilNumber!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Mobil Number", Toast.LENGTH_SHORT).show()
            } else if (email!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show()

            } else if (password!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()

            } else if (password!!.length < 8) {
                Toast.makeText(this, "Minimum 8 Character Password", Toast.LENGTH_SHORT).show()

            } else if (!password!!.matches(".*[A-Z].*".toRegex())) {

                Toast.makeText(this, "Must Contain 1 Upper-case Character", Toast.LENGTH_SHORT)
                    .show()

            } else if (!password!!.matches(".*[a-z].*".toRegex())) {
                Toast.makeText(this, "Must Contain 1 Lower-case Character", Toast.LENGTH_SHORT)
                    .show()

            } else if (!password!!.matches(".*[@#\$%^&+=].*".toRegex())) {
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
                            userName!!,
                            mobilNumber!!,
                            email!!,
                            password!!,
                            startDate!!,
                            endDate!!,
                            packageType!!,
                            loginDeviceName!!,
                            userId!!,
                            channelLogo!!,
                            channelName!!,
                            repoterName!!,
                            facebookLink!!,
                            twitterLink!!,
                            instagramLink!!,
                            youtubeLink!!,
                            websiteLink!!
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


                } else if (flag == 2) {
                    loginDeviceName = createAccountBinding.txtLoginDeviceName.text.toString()
                    mDbRef.child("user").child(userId!!).setValue(
                        UserModelClass(
                            userName!!,
                            mobilNumber!!,
                            email!!,
                            password!!,
                            startDate!!,
                            endDate!!,
                            packageType!!,
                            loginDeviceName!!,
                            userId!!,
                            channelLogo!!,
                            channelName!!,
                            repoterName!!,
                            facebookLink!!,
                            twitterLink!!,
                            instagramLink!!,
                            youtubeLink!!,
                            websiteLink!!
                        )
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Record Insert Successfully",
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
                } else {
                    auth.createUserWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information

                                addUserToDatabase(
                                    userName!!,
                                    mobilNumber!!,
                                    email!!,
                                    password!!,
                                    startDate!!,
                                    endDate!!,
                                    packageType,
                                    loginDeviceName!!,
                                    auth.currentUser?.uid!!,
                                    channelLogo!!,
                                    channelName!!,
                                    repoterName!!,
                                    facebookLink!!,
                                    twitterLink!!,
                                    instagramLink!!,
                                    youtubeLink!!,
                                    websiteLink!!
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


        createAccountBinding.imgUserChannel.setOnClickListener {
            val selectId: Int = createAccountBinding.rgPackage.checkedRadioButtonId
            var selectedRadioButton: RadioButton = findViewById(selectId)
            var packageSelected = selectedRadioButton.text.toString()
//            if (flag == 1) {
            var i = Intent(this, UserChannelActivity::class.java)
            i.putExtra("uid", userId)
            i.putExtra("flag", flag)
            i.putExtra("userName", userName)
            i.putExtra("mobilNumber", mobilNumber)
            i.putExtra("email", email)
            i.putExtra("password", password)
            i.putExtra("startDate", createAccountBinding.txtStartDate.text.toString())
            i.putExtra("endDate", createAccountBinding.txtEndDate.text.toString())
            i.putExtra("packageType", packageSelected)
            startActivity(i)
//            }else if (flag  == 2){
//                var i = Intent(this, UserChannelActivity::class.java)
//                i.putExtra("uid", userId)
//                startActivity(i)
//            }
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
        mobilNumber: String,
        email: String,
        password: String,
        startDate: String,
        endDate: String,
        packageType: String?,
        login_device_name: String,
        uid: String,
        channelLogo: String,
        channelName: String,
        repoterName: String,
        facebookLink: String,
        twitterLink: String,
        instagramLink: String,
        youtubeLink: String,
        websiteLink: String
    ) {


        mDbRef.child("user").child(uid).setValue(
            UserModelClass(
                userName,
                mobilNumber,
                email,
                password,
                startDate,
                endDate,
                packageType!!,
                login_device_name,
                uid,
                channelLogo,
                channelName,
                repoterName,
                facebookLink,
                twitterLink,
                instagramLink,
                youtubeLink,
                websiteLink
            )
        )


    }

//    private fun layoutBackgroundChange() {
//
//        createAccountBinding.linUserName.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
//        }
//        createAccountBinding.linChannel.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
//        }
//
//        createAccountBinding.linMobileNumber.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
//        }
//        createAccountBinding.linEmail.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
//        }
//
//        createAccountBinding.linPassword.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_gray_corner)
//        }
//
//        createAccountBinding.linConPassword.setOnClickListener {
//            createAccountBinding.linUserName.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linChannel.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linMobileNumber.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linEmail.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linPassword.setBackgroundResource(R.drawable.card_gray_corner)
//            createAccountBinding.linConPassword.setBackgroundResource(R.drawable.card_corner)
//        }
//
//
//    }
}
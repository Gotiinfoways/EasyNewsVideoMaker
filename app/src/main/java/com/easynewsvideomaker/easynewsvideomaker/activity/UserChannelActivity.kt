package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityUserChannelBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.modelClass.SignupUserModel
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserChannelModel
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class UserChannelActivity : AppCompatActivity() {

    lateinit var userChannelBinding: ActivityUserChannelBinding


    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    lateinit var storageReference: StorageReference

    lateinit var filePath: Uri
    var channelLogo: String? = null
    var imageSelected = 0
    var flag = 0    //  flag variable  define
    lateinit var progressDialog: Dialog

    var uid: String? = null
    var userName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var packageType: String? = null
    var loginDeviceName: String? = null
    var userBlock: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userChannelBinding = ActivityUserChannelBinding.inflate(layoutInflater)
        setContentView(userChannelBinding.root)


        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Initialize Firebase Auth
        auth = Firebase.auth
        // get the Firebase  storage reference
        storageReference = FirebaseStorage.getInstance().reference


        uid = intent.getStringExtra("uid")
        flag = intent.getIntExtra("flag", 0)
        userName = intent.getStringExtra("userName")
        mobilNumber = intent.getStringExtra("mobilNumber")
        email = intent.getStringExtra("email")
        password = intent.getStringExtra("password")
        startDate = intent.getStringExtra("startDate")
        endDate = intent.getStringExtra("endDate")
        packageType = intent.getStringExtra("packageType")


        Log.e("TAG", "GetData: $startDate  $endDate  $packageType")
        if (uid != null) {
            if (flag == 1) {
                // Attach a ValueEventListener to retrieve user data
                mDbRef.child("user").child(uid!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val user = dataSnapshot.getValue(UserModelClass::class.java)
                                if (user != null) {
                                    // User data retrieved successfully
                                    userName = user.userName!!
                                    mobilNumber = user.mobilNumber!!
                                    email = user.email!!
                                    password = user.password!!
                                    loginDeviceName = user.login_device_name!!
                                    userBlock = user.userBlock!!

                                    channelLogo = user.channelLogo
                                    val channelName = user.channelName
                                    val repoterName = user.repoterName
                                    val facebookLink = user.facebookLink
                                    val twitterLink = user.twitterLink
                                    val instagramLink = user.instagramLink
                                    val youtubeLink = user.youtubeLink
                                    val websiteLink = user.websiteLink
                                    // Handle the user data as needed

//                                userChannelBinding.edtUserName.setText(channelLogo)
                                    Glide.with(this@UserChannelActivity).load(channelLogo)
                                        .into(userChannelBinding.imgUserLogo);
                                    userChannelBinding.edtChannelName.setText(channelName)  //variable set in text view
                                    userChannelBinding.edtRepoterName.setText(repoterName)  //variable set in text view
                                    userChannelBinding.edtFacebook.setText(facebookLink)  //variable set in text view
                                    userChannelBinding.edtTwitter.setText(twitterLink)  //variable set in text view
                                    userChannelBinding.edtInstagram.setText(instagramLink)  //variable set in text view
                                    userChannelBinding.edtYoutube.setText(youtubeLink)  //variable set in text view
                                    userChannelBinding.edtWebsite.setText(websiteLink)  //variable set in text view
                                }
                            } else {
                                // User data does not exist
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle any errors, such as permission issues or network problems
                        }
                    })
            } else if (flag == 2) {
                // Attach a ValueEventListener to retrieve user data
                mDbRef.child("signup_user").child(uid!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val user = dataSnapshot.getValue(UserModelClass::class.java)
                                if (user != null) {
                                    // User data retrieved successfully
                                    userName = user.userName!!
                                    mobilNumber = user.mobilNumber!!
                                    email = user.email!!
                                    password = user.password!!
                                    loginDeviceName = user.login_device_name!!
                                    userBlock = user.userBlock!!

                                    channelLogo = user.channelLogo
                                    val channelName = user.channelName
                                    val repoterName = user.repoterName
                                    val facebookLink = user.facebookLink
                                    val twitterLink = user.twitterLink
                                    val instagramLink = user.instagramLink
                                    val youtubeLink = user.youtubeLink
                                    val websiteLink = user.websiteLink
                                    // Handle the user data as needed

//                                userChannelBinding.edtUserName.setText(channelLogo)
                                    Glide.with(this@UserChannelActivity).load(channelLogo)
                                        .into(userChannelBinding.imgUserLogo);
                                    userChannelBinding.edtChannelName.setText(channelName)  //variable set in text view
                                    userChannelBinding.edtRepoterName.setText(repoterName)  //variable set in text view
                                    userChannelBinding.edtFacebook.setText(facebookLink)  //variable set in text view
                                    userChannelBinding.edtTwitter.setText(twitterLink)  //variable set in text view
                                    userChannelBinding.edtInstagram.setText(instagramLink)  //variable set in text view
                                    userChannelBinding.edtYoutube.setText(youtubeLink)  //variable set in text view
                                    userChannelBinding.edtWebsite.setText(websiteLink)  //variable set in text view
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
        } else if (intent != null && intent.hasExtra("signupPage")) {
            flag = 3
            userName = intent.getStringExtra("userName")!!
            mobilNumber = intent.getStringExtra("mobilNumber")!!
            email = intent.getStringExtra("email")!!
            password = intent.getStringExtra("password")!!
            loginDeviceName = intent.getStringExtra("login_device_name")!!
            userBlock = intent.getStringExtra("userBlock")!!
        }
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
    }

    // UploadImage method
    private fun uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            progressDialog.setCancelable(false)

            // Defining the child of storageReference
            val ref = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnCompleteListener {

//                it.result.uploadSessionUri

                ref.downloadUrl.addOnSuccessListener {

                    channelLogo = it.toString()
                    Log.e("TAG", "uploadImage: " + channelLogo)
                }
            }
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast.makeText(this, "Image Uploaded!!", Toast.LENGTH_SHORT).show()
                    imageSelected = 1
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded " + progress.toInt() + "%"
                    )
                }
        }
    }

    private fun initView() {
        var sharedPreferences = getSharedPreferences("MySharePref", AppCompatActivity.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLogin", false) == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

        userChannelBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
        userChannelBinding.linUserLogo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery_Launcher.launch(intent)
        }

        userChannelBinding.cdUploadBtn.setOnClickListener {

            uploadImage()

        }



        userChannelBinding.cdSaveBtn.setOnClickListener {
            var channelLogo = channelLogo
            var channelName = userChannelBinding.edtChannelName.text.toString()
            var repoterName = userChannelBinding.edtRepoterName.text.toString()
            var facebookLink = userChannelBinding.edtFacebook.text.toString()
            var twitterLink = userChannelBinding.edtTwitter.text.toString()
            var instagramLink = userChannelBinding.edtInstagram.text.toString()
            var youtubeLink = userChannelBinding.edtYoutube.text.toString()
            var websiteLink = userChannelBinding.edtWebsite.text.toString()


            if (channelLogo != null) {
                if (channelName.isEmpty()) {
                    Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_SHORT).show()
                } else if (repoterName.isEmpty()) {
                    Toast.makeText(this, "Please Enter Repoter Name", Toast.LENGTH_SHORT).show()
                } else {

//                if (flag == 1) {
//                    if (intent != null && intent.hasExtra("signupPage")) {
//
//                        var userName = intent.getStringExtra("userName")
//                        var mobilNumber = intent.getStringExtra("mobilNumber")
//                        var email = intent.getStringExtra("email")
//                        var password = intent.getStringExtra("password")
//                        var login_device_name = intent.getStringExtra("login_device_name")

//                        progressDialog.show()
//                        auth.createUserWithEmailAndPassword(email!!, password!!)
//                            .addOnCompleteListener(this) { task ->
//                                if (task.isSuccessful) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    addUserToDatabase(
//                                        userName!!,
//                                        mobilNumber!!,
//                                        email,
//                                        password,
//                                        login_device_name!!,
//                                        auth.currentUser?.uid!!,
//                                        channelLogo,
//                                        channelName,
//                                        repoterName,
//                                        facebookLink,
//                                        twitterLink,
//                                        instagramLink,
//                                        youtubeLink,
//                                        websiteLink
//
//                                    )
//
//
//                                    var myEdit: SharedPreferences.Editor =
//                                        sharedPreferences.edit()
//                                    myEdit.putBoolean("isLogin", true)
//                                    myEdit.commit()
//
//                                    Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT)
//                                        .show()
//
//                                    progressDialog.dismiss()
//
//                                    var i = Intent(this, HomeActivity::class.java)
//                                    startActivity(i)
//                                    finish()
//                                }
//                            }.addOnFailureListener {
//                                Log.e("TAG", "SignUp:Fail  " + it.message)
//                                Toast.makeText(this, "SignUp Fail", Toast.LENGTH_SHORT).show()
//                                progressDialog.dismiss()
//                            }
//                    }
                    if (flag == 1) {
                        progressDialog.show()
                        mDbRef.child("user").child(uid!!).setValue(
                            UserModelClass(
                                userName!!,
                                mobilNumber!!,
                                email!!,
                                password!!,
                                startDate!!,
                                endDate!!,
                                packageType!!,
                                loginDeviceName!!,
                                userBlock!!,
                                uid!!,
                                channelLogo!!,
                                channelName,
                                repoterName,
                                facebookLink,
                                twitterLink,
                                instagramLink,
                                youtubeLink,
                                websiteLink
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
                        mDbRef.child("user").child(uid!!).setValue(
                            UserModelClass(
                                userName!!,
                                mobilNumber!!,
                                email!!,
                                password!!,
                                startDate!!,
                                endDate!!,
                                packageType!!,
                                loginDeviceName!!,
                                userBlock!!,
                                uid!!,
                                channelLogo!!,
                                channelName,
                                repoterName,
                                facebookLink,
                                twitterLink,
                                instagramLink,
                                youtubeLink,
                                websiteLink
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
                                    deleteSignUpUserData(uid!!)
                                    progressDialog.dismiss()
                                    var i = Intent(this, AdminHomeActivity::class.java)
                                    startActivity(i)
                                }
                            }.addOnFailureListener {
                                Log.e("TAG", "initView: " + it.message)
                                progressDialog.dismiss()
                            }
                    } else if (flag == 3) {
                        progressDialog.show()
                        auth.createUserWithEmailAndPassword(email!!, password!!)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    addUserToDatabase(
                                        userName!!,
                                        mobilNumber!!,
                                        email!!,
                                        password!!,
                                        loginDeviceName!!,
                                        userBlock!!,
                                        auth.currentUser?.uid!!,
                                        channelLogo!!,
                                        channelName,
                                        repoterName,
                                        facebookLink,
                                        twitterLink,
                                        instagramLink,
                                        youtubeLink,
                                        websiteLink

                                    )


                                    var myEdit: SharedPreferences.Editor =
                                        sharedPreferences.edit()
                                    myEdit.putBoolean("isLogin", true)
                                    myEdit.commit()

                                    Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT)
                                        .show()

                                    progressDialog.dismiss()

                                    var i = Intent(this, HomeActivity::class.java)
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
            } else {
                Toast.makeText(this, "Please First Select image and upload", Toast.LENGTH_SHORT)
                    .show()
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

    var gallery_Launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.getResultCode() === RESULT_OK) {
                val data: Intent = result.getData()!!
                filePath = data.data!!
//                var selectedImageUri = getImagePathFromURI(uri!!)
                userChannelBinding.imgUserLogo.setImageURI(filePath)
                userChannelBinding.txtUserLogo.visibility = View.GONE
                userChannelBinding.cdUploadBtn.visibility = View.VISIBLE
            }
        })

    //    private fun getImagePathFromURI(contentUri: Uri): String? {
//        val projection = arrayOf(MediaStore.Video.Media.DATA)
//        val cursor: Cursor? =
//            requireContext().contentResolver.query(contentUri, projection, null, null, null)
//
//
//        cursor?.use {
//            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            it.moveToFirst()
//            imagePath = it.getString(columnIndex)
//            Log.e("TAG", "getPath:${imagePath} ")
//            return imagePath
//        }
//
//        // If the cursor is null, the query failed
//        return contentUri.path // Fallback to using the URI's path
//    }
    private fun addUserToDatabase(
        userName: String,
        mobilNumber: String,
        email: String,
        password: String,
        loginDeviceName: String,
        userBlock: String,
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


        mDbRef.child("signup_user").child(uid).setValue(
            SignupUserModel(
                userName,
                mobilNumber,
                email,
                password,
                loginDeviceName,
                userBlock,
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
}
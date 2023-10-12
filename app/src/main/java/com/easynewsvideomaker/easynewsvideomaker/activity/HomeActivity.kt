package com.easynewsvideomaker.easynewsvideomaker.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.News_Poster_Activity
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityHomeBinding
import com.easynewsvideomaker.easynewsvideomaker.fragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.io.File


class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var PERMISSION_REQUEST_CODE = 1

    //initialize root directory
    var rootDir = Environment.getExternalStorageDirectory().path
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        //making sure the download directory exists
        checkAndCreateDirectory("/Easy News Maker")
        initView()
        navView()
    }

    //function to verify if directory exists
    fun checkAndCreateDirectory(dirName: String) {
        val new_dir: File = File(rootDir + dirName)
        if (!new_dir.exists()) {
            new_dir.mkdirs()
        }
    }

    private fun initView() {


        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.container, HomeFragment())
        transaction.commit()

        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (checkPermissionVersion()) {
                Toast.makeText(this, "Permission already granted .", Toast.LENGTH_LONG).show()

            } else {

                requestPermission()

            }
        } else if (SDK_INT <= Build.VERSION_CODES.R) {
            if (checkPermission()) {

                Toast.makeText(this, "Permission already granted .", Toast.LENGTH_LONG).show()

            } else {

                requestPermission()

            }
        }
    }


    private fun navView() {
        homeBinding.imgMenu.setOnClickListener {
            homeBinding.drawer.openDrawer(GravityCompat.START)

        }

        homeBinding.layNewsPosterNav.setOnClickListener {
            var i = Intent(this, News_Poster_Activity::class.java)
            startActivity(i)
        }

        //  Subscription / Package
        homeBinding.imgSubscription.setOnClickListener {
            var i = Intent(this, SubscriptionActivity::class.java)
            startActivity(i)
        }


        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    var firstName = postSnapshot.child("userName").value
                    var channelName = postSnapshot.child("channelName").value

                    homeBinding.txtUserName.text = firstName.toString()
                    homeBinding.txtCompanyName.text = channelName.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

//        Subscription
        homeBinding.laySubscriptionNav.setOnClickListener {
            var i = Intent(this, SubscriptionActivity::class.java)
            startActivity(i)
        }


//        Share App
        homeBinding.layShareNav.setOnClickListener {
            val appPackageName: String = getPackageName()
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out the App at: https://play.google.com/store/apps/details?id=$appPackageName"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
//Logout
//        homeBinding.layLogoutNav.setOnClickListener {
//            var sharedPreferences = getSharedPreferences(
//                "MySharePref",
//                AppCompatActivity.MODE_PRIVATE
//            )
//            var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
//            myEdit.remove("isLogin")
//            myEdit.commit()
//            mDbRef.child("user").child(auth.currentUser?.uid!!).child("device_token").removeValue()
//            auth.signOut()
//
//            Toast.makeText(this, "User Logout", Toast.LENGTH_SHORT).show()
//            var i = Intent(this, LoginScreenActivity::class.java)
//            startActivity(i)
//        }

        homeBinding.layLogoutNav.setOnClickListener {

//            val user = FirebaseAuth.getInstance().currentUser
//            val userId = user?.uid // Get the user's unique ID

//            val userRef = FirebaseDatabase.getInstance().getReference("user").child(userId!!)
//            userRef.child("deviceCount").addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val deviceCount = dataSnapshot.getValue(Int::class.java) ?: 0
//
//                    if (deviceCount > 0) {
//                        // Decrement the device count
//                        userRef.child("deviceCount").setValue(deviceCount - 1)
            var sharedPreferences = getSharedPreferences(
                "MySharePref",
                AppCompatActivity.MODE_PRIVATE
            )
            var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
            myEdit.remove("isLogin")
            myEdit.commit()
            auth.signOut()
            Toast.makeText(this@HomeActivity, "User Logout", Toast.LENGTH_SHORT).show()
            var i = Intent(this@HomeActivity, LoginScreenActivity::class.java)
            startActivity(i)
            // Proceed with the logout

//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Handle database errors
//            Toast.makeText(this@HomeActivity, "User Logout Fail", Toast.LENGTH_SHORT).show()
//                }
//            })


        }
        homeBinding.gotiinfoways.setOnClickListener(View.OnClickListener {
            val url = "https://www.gotiinfoways.com/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        })

    }

    private fun checkPermission(): Boolean {

        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )
        val result1 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        val result2 = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        val result3 = ContextCompat.checkSelfPermission(applicationContext, RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionVersion(): Boolean {

        val result2 = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        val result3 = ContextCompat.checkSelfPermission(applicationContext, RECORD_AUDIO)
        return result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {

        if (SDK_INT >= Build.VERSION_CODES.R) {

            if (Environment.isExternalStorageManager()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else { //request for the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        CAMERA,
                        RECORD_AUDIO,
                        READ_MEDIA_AUDIO, READ_MEDIA_IMAGES, READ_MEDIA_VIDEO
                    ),
                    200
                )
            }

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    WRITE_EXTERNAL_STORAGE,
                    READ_EXTERNAL_STORAGE,
                    CAMERA,
                    RECORD_AUDIO
                ),
                100
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> if (grantResults.size > 0) {

                val writeExternalStorage =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readExternalStorage =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                val camera = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val record = grantResults[3] == PackageManager.PERMISSION_GRANTED

                if (writeExternalStorage && readExternalStorage && camera && record)
                    Toast.makeText(
                        this,
                        "Permission Granted",
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    Toast.makeText(
                        this,
                        "Permission Denied",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

            200 -> if (grantResults.size > 0) {
                val camera = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val record = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val audio = grantResults[3] == PackageManager.PERMISSION_GRANTED
                val image = grantResults[4] == PackageManager.PERMISSION_GRANTED
                val video = grantResults[5] == PackageManager.PERMISSION_GRANTED

                if (camera && record && audio && image && video)
                    Toast.makeText(
                        this,
                        "Permission Granted",
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    Toast.makeText(
                        this,
                        "Permission Denied",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }
    }


}
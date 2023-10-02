package com.easynewsvideomaker.easynewsvideomaker.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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

class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        initView()
        navView()
    }

    private fun initView() {


        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.container, HomeFragment())
        transaction.commit()

        if (checkPermission()) {

            Toast.makeText(this, "Permission already granted.", Toast.LENGTH_LONG).show()

        } else {

            requestPermission()
        }
    }


    private fun navView() {
        homeBinding.imgMenu.setOnClickListener {
            homeBinding.drawer.openDrawer(GravityCompat.START)

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
        homeBinding.layLogoutNav.setOnClickListener {
            var sharedPreferences = getSharedPreferences(
                "MySharePref",
                AppCompatActivity.MODE_PRIVATE
            )
            var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
            myEdit.remove("isLogin")
            myEdit.commit()
            mDbRef.child("user").child(auth.currentUser?.uid!!).child("device_token").removeValue()
            auth.signOut()

            Toast.makeText(this, "User Logout", Toast.LENGTH_SHORT).show()
            var i = Intent(this, LoginScreenActivity::class.java)
            startActivity(i)
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
        val result3 =
            ContextCompat.checkSelfPermission(applicationContext, RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
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
        }
    }
}
package com.easynewsvideomaker.easynewsvideomaker.reels_maker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.activity.GoldenPackageActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.HomeActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.MainActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.SubscriptionActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityReelsMakerBinding
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame1Fragment
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

class ReelsMakerActivity : AppCompatActivity() {
    lateinit var reelsMakerBinding: ActivityReelsMakerBinding
    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var packageType: String? = null
    var userBlock: String? = null

    //initialize root directory
    var rootDir = Environment.getExternalStorageDirectory().path
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reelsMakerBinding = ActivityReelsMakerBinding.inflate(layoutInflater)
        setContentView(reelsMakerBinding.root)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
        //making sure the download directory exists
        checkAndCreateDirectory("/Easy News Maker/Reels Maker")
        initView()
    }

    //function to verify if directory exists
    fun checkAndCreateDirectory(dirName: String) {
        val new_dir: File = File(rootDir + dirName)
        if (!new_dir.exists()) {
            new_dir.mkdirs()
        }
    }

    private fun initView() {
        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    packageType = postSnapshot.child("packageType").value.toString()
                    userBlock = postSnapshot.child("userBlock").value.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        reelsMakerBinding.imgBack.setOnClickListener {
            onBackPressed()
        }


        reelsMakerBinding.frame1.setOnClickListener(View.OnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_1_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        })

        reelsMakerBinding.frame2.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_2_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }

        reelsMakerBinding.frame3.setOnClickListener(View.OnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_3_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        })

        reelsMakerBinding.frame4.setOnClickListener(View.OnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_4_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        })

        reelsMakerBinding.frame5.setOnClickListener(View.OnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_5_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        })

        reelsMakerBinding.frame6.setOnClickListener(View.OnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(this, Insta_6_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(this, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(this, SubscriptionActivity::class.java)
                startActivity(i)
            }
        })

    }
}
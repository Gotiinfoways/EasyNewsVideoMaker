package com.easynewsvideomaker.easynewsvideomaker.reels_maker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.activity.HomeActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.MainActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityReelsMakerBinding
import java.io.File

class ReelsMakerActivity : AppCompatActivity() {
    lateinit var reelsMakerBinding: ActivityReelsMakerBinding

    //initialize root directory
    var rootDir = Environment.getExternalStorageDirectory().path
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reelsMakerBinding= ActivityReelsMakerBinding.inflate(layoutInflater)
        setContentView(reelsMakerBinding.root)

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

        reelsMakerBinding.imgBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        reelsMakerBinding.frame1!!.setOnClickListener(View.OnClickListener {
            val intentFrame1 = Intent(this,Insta_1_Activity::class.java)
            startActivity(intentFrame1)
        })

        reelsMakerBinding.frame2.setOnClickListener {
            val intentFram2 = Intent(this,Insta_2_Activity::class.java)
            startActivity(intentFram2)
        }

        reelsMakerBinding.frame3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Insta_3_Activity::class.java)
            startActivity(intent)
        })

        reelsMakerBinding.frame4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Insta_4_Activity::class.java)
            startActivity(intent)
        })

        reelsMakerBinding.frame5.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Insta_5_Activity::class.java)
            startActivity(intent)
        })

        reelsMakerBinding.frame6.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Insta_6_Activity::class.java)
            startActivity(intent)
        })

    }
}
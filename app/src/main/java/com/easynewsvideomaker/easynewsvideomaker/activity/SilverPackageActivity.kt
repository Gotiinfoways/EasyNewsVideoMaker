package com.easynewsvideomaker.easynewsvideomaker.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivitySilverPackageBinding

class SilverPackageActivity : AppCompatActivity() {
    lateinit var silverPackageBinding: ActivitySilverPackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        silverPackageBinding= ActivitySilverPackageBinding.inflate(layoutInflater)
        setContentView(silverPackageBinding.root)

        initView()
    }

    private fun initView() {
        silverPackageBinding.imgBack.setOnClickListener {
            onBackPressed()
        }



    }
}
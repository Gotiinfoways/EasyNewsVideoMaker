package com.easynewsvideomaker.easynewsvideomaker.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityGoldenPackageBinding


class GoldenPackageActivity : AppCompatActivity() {
    lateinit var goldenPackageBinding: ActivityGoldenPackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goldenPackageBinding = ActivityGoldenPackageBinding.inflate(layoutInflater)
        setContentView(goldenPackageBinding.root)

        initView()
    }

    private fun initView() {
        goldenPackageBinding.imgBack.setOnClickListener {
            onBackPressed()
        }



    }
}
package com.easynewsvideomaker.easynewsvideomaker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.easynewsvideomaker.easynewsvideomaker.ComparePackageActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivitySubscriptionBinding

class SubscriptionActivity : AppCompatActivity() {
    lateinit var subscriptionBinding: ActivitySubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptionBinding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(subscriptionBinding.root)

        initView()
    }

    private fun initView() {



        subscriptionBinding.imgBack.setOnClickListener {
            onBackPressed()
        }

        subscriptionBinding.imgGoldenPackage.setOnClickListener {
            var i = Intent(this, GoldenPackageActivity::class.java)
            startActivity(i)
        }
        subscriptionBinding.imgSilverPackage.setOnClickListener {
            var i = Intent(this, SilverPackageActivity::class.java)
            startActivity(i)
        }
        subscriptionBinding.imgComparePackage.setOnClickListener {
            var i = Intent(this, ComparePackageActivity::class.java)
            startActivity(i)
        }
    }
}
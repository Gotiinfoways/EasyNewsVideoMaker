package com.easynewsvideomaker.easynewsvideomaker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    lateinit var notificationBinding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding= ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)

        initView()
    }

    private fun initView() {
        notificationBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}
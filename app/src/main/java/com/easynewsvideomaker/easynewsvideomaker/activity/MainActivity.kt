package com.easynewsvideomaker.easynewsvideomaker.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initView()
    }

    private fun initView() {

        //status bar color change
        val window = window
        window.statusBarColor = Color.BLACK

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, LoginScreenActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
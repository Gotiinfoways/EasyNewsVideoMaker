package com.easynewsvideomaker.easynewsvideomaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityComparePackageBinding

class ComparePackageActivity : AppCompatActivity() {

    lateinit var comparePackageBinding: ActivityComparePackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comparePackageBinding= ActivityComparePackageBinding.inflate(layoutInflater)
        setContentView(comparePackageBinding.root)

        initView()
    }

    private fun initView() {
        comparePackageBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}
package com.easynewsvideomaker.easynewsvideomaker.news_poster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.easynewsvideomaker.easynewsvideomaker.R

class News_Poster_Activity : AppCompatActivity() {

    lateinit var post1: ImageView
    lateinit var post2: ImageView
    lateinit var post3: ImageView
    lateinit var post4: ImageView
    lateinit var post5: ImageView
    lateinit var post6: ImageView
    lateinit var post7: ImageView
    lateinit var post8: ImageView
    lateinit var post9: ImageView
    lateinit var post10: ImageView
    lateinit var post11: ImageView
    lateinit var post12: ImageView
    lateinit var post13: ImageView
    lateinit var post14: ImageView
    lateinit var post15: ImageView
    lateinit var post16: ImageView
    lateinit var post17: ImageView
    lateinit var post18: ImageView
    lateinit var post19: ImageView
    lateinit var post20: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_poster)

        post1 = findViewById(R.id.post1)
        post2 = findViewById(R.id.post2)
        post3 = findViewById(R.id.post3)
        post4 = findViewById(R.id.post4)
        post5 = findViewById(R.id.post5)
        post6 = findViewById(R.id.post6)
        post7 = findViewById(R.id.post7)
        post8 = findViewById(R.id.post8)
        post9 = findViewById(R.id.post9)
        post10 = findViewById(R.id.post10)
        post11 = findViewById(R.id.post11)
        post12 = findViewById(R.id.post12)
        post13 = findViewById(R.id.post13)
        post14 = findViewById(R.id.post14)
        post15 = findViewById(R.id.post15)
        post16 = findViewById(R.id.post16)
        post17 = findViewById(R.id.post17)
        post18 = findViewById(R.id.post18)
        post19 = findViewById(R.id.post19)
        post20 = findViewById(R.id.post20)


        post1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_1_Activity::class.java)
            startActivity(intent)
        })

        post2.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_2_Activity::class.java)
            startActivity(intent)
        }

        post3.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_3_Activity::class.java)
            startActivity(intent)
        }

        post4.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_4_Activity::class.java)
            startActivity(intent)
        }

        post5.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_5_Activity::class.java)
            startActivity(intent)
        }

        post6.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_6_Activity::class.java)
            startActivity(intent)
        }

        post7.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_7_Activity::class.java)
            startActivity(intent)
        }

        post8.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_8_Activity::class.java)
            startActivity(intent)
        }

        post9.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_9_Activity::class.java)
            startActivity(intent)
        }

        post10.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_10_Activity::class.java)
            startActivity(intent)
        }

        post11.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_11_Activity::class.java)
            startActivity(intent)
        }

        post12.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_12_Activity::class.java)
            startActivity(intent)
        }

        post13.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_13_Activity::class.java)
            startActivity(intent)
        }

        post14.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_14_Activity::class.java)
            startActivity(intent)
        }

        post15.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_15_Activity::class.java)
            startActivity(intent)
        }

        post16.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_16_Activity::class.java)
            startActivity(intent)
        }

        post17.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_17_Activity::class.java)
            startActivity(intent)
        }

        post18.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_18_Activity::class.java)
            startActivity(intent)
        }

        post19.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_19_Activity::class.java)
            startActivity(intent)
        }

        post20.setOnClickListener {
            val intent = Intent(this@News_Poster_Activity, Post_20_Activity::class.java)
            startActivity(intent)
        }

    }
}
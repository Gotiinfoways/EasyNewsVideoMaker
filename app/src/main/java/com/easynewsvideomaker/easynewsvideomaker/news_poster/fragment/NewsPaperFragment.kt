package com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentNewsPaperBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_10_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_11_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_12_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_13_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_14_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_15_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_16_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_17_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_18_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_19_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_20_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_5_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_6_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_7_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_8_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_9_Activity


class NewsPaperFragment : Fragment() {

    lateinit var newsPaperBinding: FragmentNewsPaperBinding
    var newsPaperList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsPaperBinding = FragmentNewsPaperBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        return newsPaperBinding.root
    }

    private fun initView() {
        insertImageInList()

        var selectedFragment = "newsPaperFragment"
        var adapter = NewsAdapter(requireContext(),selectedFragment) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        newsPaperBinding.rcvView.layoutManager = manger
        newsPaperBinding.rcvView.adapter = adapter

        adapter.updateList(newsPaperList)

    }

    private fun insertImageInList() {

        newsPaperList.apply {
            add(R.drawable.np1)
            add(R.drawable.np2)
            add(R.drawable.np3)
            add(R.drawable.np4)
            add(R.drawable.np5)
            add(R.drawable.np6)
            add(R.drawable.np7)
            add(R.drawable.np8)
            add(R.drawable.np9)
            add(R.drawable.np10)
            add(R.drawable.np11)
            add(R.drawable.np12)
            add(R.drawable.np13)
            add(R.drawable.np14)
            add(R.drawable.np15)
            add(R.drawable.np16)
            add(R.drawable.np17)
            add(R.drawable.np18)
            add(R.drawable.np19)
            add(R.drawable.np20)
            add(R.drawable.np21)
            add(R.drawable.np22)
            add(R.drawable.np23)
            add(R.drawable.np24)
            add(R.drawable.np25)
            add(R.drawable.np26)
            add(R.drawable.np27)
            add(R.drawable.np28)
            add(R.drawable.np29)
            add(R.drawable.np30)
            add(R.drawable.np31)
            add(R.drawable.np32)
            add(R.drawable.np33)
            add(R.drawable.np34)
            add(R.drawable.np35)
            add(R.drawable.np36)
            add(R.drawable.np37)
            add(R.drawable.np38)
            add(R.drawable.np39)
            add(R.drawable.np40)
            add(R.drawable.np40)
            add(R.drawable.np40)
            add(R.drawable.np41)
            add(R.drawable.np42)
            add(R.drawable.np43)
            add(R.drawable.np44)
            add(R.drawable.np45)
            add(R.drawable.np46)
            add(R.drawable.np47)
            add(R.drawable.np48)
            add(R.drawable.np49)
            add(R.drawable.np50)
            add(R.drawable.np51)
            add(R.drawable.np52)
            add(R.drawable.np53)
            add(R.drawable.np54)
            add(R.drawable.np55)
            add(R.drawable.np56)
            add(R.drawable.np57)
            add(R.drawable.np58)

        }
    }

    private fun clickPage(it: Int) {
//
//        if (it == 0) {
//            val intent = Intent(requireContext(), Post_1_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 1) {
//            val intent = Intent(requireContext(), Post_2_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 2) {
//            val intent = Intent(requireContext(), Post_3_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 3) {
//            val intent = Intent(requireContext(), Post_4_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 4) {
//            val intent = Intent(requireContext(), Post_5_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 5) {
//            val intent = Intent(requireContext(), Post_6_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 6) {
//            val intent = Intent(requireContext(), Post_7_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 7) {
//            val intent = Intent(requireContext(), Post_8_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 8) {
//            val intent = Intent(requireContext(), Post_9_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 9) {
//            val intent = Intent(requireContext(), Post_10_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 10) {
//            val intent = Intent(requireContext(), Post_11_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 11) {
//            val intent = Intent(requireContext(), Post_12_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 12) {
//            val intent = Intent(requireContext(), Post_13_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 13) {
//            val intent = Intent(requireContext(), Post_14_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 14) {
//            val intent = Intent(requireContext(), Post_15_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 15) {
//            val intent = Intent(requireContext(), Post_16_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 16) {
//            val intent = Intent(requireContext(), Post_17_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 17) {
//            val intent = Intent(requireContext(), Post_18_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 18) {
//            val intent = Intent(requireContext(), Post_19_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 19) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 20) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 21) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 22) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 23) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 24) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 25) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 26) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 27) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 28) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 29) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 30) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 31) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 32) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 33) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 34) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 35) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 36) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 37) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 38) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 39) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 40) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 41) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 42) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 43) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 44) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 45) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 46) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 47) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 48) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 49) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 50) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 51) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 52) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 56) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 57) {
//            val intent = Intent(requireContext(), Post_20_Activity::class.java)
//            startActivity(intent)
//        }


    }
}
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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentNewsBinding
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


class NewsFragment : Fragment() {
 lateinit var newsBinding: FragmentNewsBinding

    var newsList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsBinding= FragmentNewsBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment

        initView()
        return newsBinding.root
    }

    private fun initView() {

        insertImageInList()

        var adapter = NewsAdapter(requireContext()) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        newsBinding.rcvView.layoutManager = manger
        newsBinding.rcvView.adapter = adapter

        adapter.updateList(newsList)
    }


    private fun insertImageInList() {

        newsList.apply {
            add(R.drawable.n1)
            add(R.drawable.n2)
            add(R.drawable.n3)
            add(R.drawable.n4)
            add(R.drawable.n5)
            add(R.drawable.n6)
            add(R.drawable.n7)
            add(R.drawable.n8)
            add(R.drawable.n9)
            add(R.drawable.n10)
            add(R.drawable.n11)
            add(R.drawable.n12)
            add(R.drawable.n13)
            add(R.drawable.n14)
            add(R.drawable.n15)
            add(R.drawable.n16)
            add(R.drawable.n17)
            add(R.drawable.n18)
            add(R.drawable.n19)
            add(R.drawable.n20)
            add(R.drawable.n21)
            add(R.drawable.n22)
            add(R.drawable.n23)
            add(R.drawable.n24)
            add(R.drawable.n25)
        }
    }

    private fun clickPage(it: Int) {

        if (it == 0) {
            val intent = Intent(requireContext(), Post_1_Activity::class.java)
            startActivity(intent)
        } else if (it == 1) {
            val intent = Intent(requireContext(), Post_2_Activity::class.java)
            startActivity(intent)
        } else if (it == 2) {
            val intent = Intent(requireContext(), Post_3_Activity::class.java)
            startActivity(intent)
        } else if (it == 3) {
            val intent = Intent(requireContext(), Post_4_Activity::class.java)
            startActivity(intent)
        } else if (it == 4) {
            val intent = Intent(requireContext(), Post_5_Activity::class.java)
            startActivity(intent)
        } else if (it == 5) {
            val intent = Intent(requireContext(), Post_6_Activity::class.java)
            startActivity(intent)
        } else if (it == 6) {
            val intent = Intent(requireContext(), Post_7_Activity::class.java)
            startActivity(intent)
        } else if (it == 7) {
            val intent = Intent(requireContext(), Post_8_Activity::class.java)
            startActivity(intent)
        } else if (it == 8) {
            val intent = Intent(requireContext(), Post_9_Activity::class.java)
            startActivity(intent)
        } else if (it == 9) {
            val intent = Intent(requireContext(), Post_10_Activity::class.java)
            startActivity(intent)
        } else if (it == 10) {
            val intent = Intent(requireContext(), Post_11_Activity::class.java)
            startActivity(intent)
        } else if (it == 11) {
            val intent = Intent(requireContext(), Post_12_Activity::class.java)
            startActivity(intent)
        } else if (it == 12) {
            val intent = Intent(requireContext(), Post_13_Activity::class.java)
            startActivity(intent)
        } else if (it == 13) {
            val intent = Intent(requireContext(), Post_14_Activity::class.java)
            startActivity(intent)
        } else if (it == 14) {
            val intent = Intent(requireContext(), Post_15_Activity::class.java)
            startActivity(intent)
        } else if (it == 15) {
            val intent = Intent(requireContext(), Post_16_Activity::class.java)
            startActivity(intent)
        } else if (it == 16) {
            val intent = Intent(requireContext(), Post_17_Activity::class.java)
            startActivity(intent)
        } else if (it == 17) {
            val intent = Intent(requireContext(), Post_18_Activity::class.java)
            startActivity(intent)
        } else if (it == 18) {
            val intent = Intent(requireContext(), Post_19_Activity::class.java)
            startActivity(intent)
        } else if (it == 19) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        } else if (it == 20) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        } else if (it == 21) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        } else if (it == 22) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        } else if (it == 23) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        }else if (it == 24) {
            val intent = Intent(requireContext(), Post_20_Activity::class.java)
            startActivity(intent)
        }


    }
}
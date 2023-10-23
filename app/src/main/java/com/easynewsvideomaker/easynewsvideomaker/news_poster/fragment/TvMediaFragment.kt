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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentTvMediaBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_10_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_5_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_6_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_7_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_8_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_9_Activity


class TvMediaFragment : Fragment() {
lateinit var   tvMediaBinding: FragmentTvMediaBinding
var tvMediaList=ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvMediaBinding= FragmentTvMediaBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment

        initView()
        return tvMediaBinding.root
    }

    private fun initView() {

        insertImageInList()

        var adapter = NewsAdapter(requireContext()) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        tvMediaBinding.rcvView.layoutManager = manger
        tvMediaBinding.rcvView.adapter = adapter

        adapter.updateList(tvMediaList)
    }


    private fun insertImageInList() {

        tvMediaList.apply {
            add(R.drawable.t1)
            add(R.drawable.t2)
            add(R.drawable.t3)
            add(R.drawable.t4)
            add(R.drawable.t5)
            add(R.drawable.t6)
            add(R.drawable.t7)
            add(R.drawable.t8)
            add(R.drawable.t9)
            add(R.drawable.t10)
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
        }


    }
}
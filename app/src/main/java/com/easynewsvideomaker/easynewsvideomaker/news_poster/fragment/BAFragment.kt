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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentBABinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity


class BAFragment : Fragment() {
    lateinit var baBinding: FragmentBABinding
    var baList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baBinding = FragmentBABinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        return baBinding.root
    }


    private fun initView() {

        insertImageInList()

        var adapter = NewsAdapter(requireContext()) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        baBinding.rcvView.layoutManager = manger
        baBinding.rcvView.adapter = adapter

        adapter.updateList(baList)
    }


    private fun insertImageInList() {

        baList.apply {
            add(R.drawable.ba1)
            add(R.drawable.ba2)
            add(R.drawable.ba3)
            add(R.drawable.ba4)
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
        }

    }
}
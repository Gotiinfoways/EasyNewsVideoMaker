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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentElcationBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity


class ElcationFragment : Fragment() {
    lateinit var elcationBinding: FragmentElcationBinding
    var elcationList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        elcationBinding = FragmentElcationBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        return elcationBinding.root
    }

    private fun initView() {

        insertImageInList()

        var adapter = NewsAdapter(requireContext()) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        elcationBinding.rcvView.layoutManager = manger
        elcationBinding.rcvView.adapter = adapter

        adapter.updateList(elcationList)
    }


    private fun insertImageInList() {

        elcationList.apply {
            add(R.drawable.e1)
            add(R.drawable.e2)
            add(R.drawable.e3)
            add(R.drawable.e4)
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
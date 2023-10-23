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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentAdvtBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity

class AdvtFragment : Fragment() {
lateinit var advtBinding: FragmentAdvtBinding
var advtList=ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        advtBinding= FragmentAdvtBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        initView()
        return advtBinding.root
    }


    private fun initView() {

        insertImageInList()

        var adapter = NewsAdapter(requireContext()) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        advtBinding.rcvView.layoutManager = manger
        advtBinding.rcvView.adapter = adapter

        adapter.updateList(advtList)
    }


    private fun insertImageInList() {

        advtList.apply {
            add(R.drawable.a1)
            add(R.drawable.a2)
            add(R.drawable.a3)
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
        }

    }
}
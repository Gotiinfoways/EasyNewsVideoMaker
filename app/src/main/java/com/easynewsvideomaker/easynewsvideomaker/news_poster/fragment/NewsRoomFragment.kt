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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentNewsRoomBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity

class NewsRoomFragment : Fragment() {
    lateinit var newsRoomBinding: FragmentNewsRoomBinding
    var newsRoomList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsRoomBinding = FragmentNewsRoomBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        return newsRoomBinding.root
    }


    private fun initView() {

        insertImageInList()

        var selectedFragment = "newsRoomFragment"
        var adapter = NewsAdapter(requireContext(),selectedFragment) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        newsRoomBinding.rcvView.layoutManager = manger
        newsRoomBinding.rcvView.adapter = adapter

        adapter.updateList(newsRoomList)
    }


    private fun insertImageInList() {

        newsRoomList.apply {
            add(R.drawable.nr1)
            add(R.drawable.nr2)
        }
    }

    private fun clickPage(it: Int) {

//        if (it == 0) {
//            val intent = Intent(requireContext(), Post_1_Activity::class.java)
//            startActivity(intent)
//        } else if (it == 1) {
//            val intent = Intent(requireContext(), Post_2_Activity::class.java)
//            startActivity(intent)
//        }


    }
}
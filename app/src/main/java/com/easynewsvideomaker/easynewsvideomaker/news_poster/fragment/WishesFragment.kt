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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentWishesBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity


class WishesFragment : Fragment() {
lateinit var wishesBinding: FragmentWishesBinding
var wishesList=ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wishesBinding= FragmentWishesBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment

        initView()
        return wishesBinding.root
    }


    private fun initView() {

        insertImageInList()

        var selectedFragment = "wishesFragment"
        var adapter = NewsAdapter(requireContext(),selectedFragment) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        wishesBinding.rcvView.layoutManager = manger
        wishesBinding.rcvView.adapter = adapter

        adapter.updateList(wishesList)
    }


    private fun insertImageInList() {

        wishesList.apply {
            add(R.drawable.w1)
            add(R.drawable.w2)
            add(R.drawable.w3)
            add(R.drawable.w4)
        }
    }

    private fun clickPage(it: Int) {

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
//        }


    }


}
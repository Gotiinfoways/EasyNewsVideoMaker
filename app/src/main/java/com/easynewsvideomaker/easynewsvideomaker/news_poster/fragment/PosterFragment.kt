package com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentPosterBinding

class PosterFragment : Fragment() {

lateinit var  posterBinding: FragmentPosterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        posterBinding=FragmentPosterBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment

        initView()
        return posterBinding.root
    }

    private fun initView() {

    }


}
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
import com.easynewsvideomaker.easynewsvideomaker.activity.GoldenPackageActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.SubscriptionActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentBABinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.ba_news.BaPoster1Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_1_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_2_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_3_Activity
import com.easynewsvideomaker.easynewsvideomaker.news_poster.poster.Post_4_Activity
import com.easynewsvideomaker.easynewsvideomaker.reels_maker.Insta_6_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class BAFragment : Fragment() {
    lateinit var baBinding: FragmentBABinding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var packageType: String? = null
    var userBlock: String? = null

    var baList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baBinding = FragmentBABinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        initView()
        return baBinding.root
    }


    private fun initView() {

        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    packageType = postSnapshot.child("packageType").value.toString()
                    userBlock = postSnapshot.child("userBlock").value.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        insertImageInList()

        var selectedFragment = "baFragment"
        var adapter = NewsAdapter(requireContext(), selectedFragment) {

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
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), BaPoster1Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 1) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), BaPoster1Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 2) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), BaPoster1Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 3) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), BaPoster1Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        }

    }
}
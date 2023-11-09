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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentPosterBinding
import com.easynewsvideomaker.easynewsvideomaker.news_poster.NewsAdapter
import com.easynewsvideomaker.easynewsvideomaker.news_poster.ba_news.BaPoster1Activity
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class PosterFragment : Fragment() {

    lateinit var posterBinding: FragmentPosterBinding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var packageType: String? = null
    var userBlock: String? = null
    var posterList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        posterBinding = FragmentPosterBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        initView()
        return posterBinding.root
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

        var selectedFragment = "posterFragment"
        var adapter = NewsAdapter(requireContext(), selectedFragment) {

            Log.e("TAG", "click: $it")
            clickPage(it)
        }

        var manger = GridLayoutManager(context, 2)
        posterBinding.rcvView.layoutManager = manger
        posterBinding.rcvView.adapter = adapter

        adapter.updateList(posterList)
    }


    private fun insertImageInList() {

        posterList.apply {
            add(R.drawable.post1)
            add(R.drawable.post2)
            add(R.drawable.post3)
            add(R.drawable.post4)
            add(R.drawable.post5)
            add(R.drawable.post6)
            add(R.drawable.post7)
            add(R.drawable.post8)
            add(R.drawable.post9)
            add(R.drawable.post10)
            add(R.drawable.post11)
            add(R.drawable.post12)
            add(R.drawable.post13)
            add(R.drawable.post14)
            add(R.drawable.post15)
            add(R.drawable.post16)
            add(R.drawable.post17)
            add(R.drawable.post18)
            add(R.drawable.post19)
            add(R.drawable.post20)
        }
    }

    private fun clickPage(it: Int) {

        if (it == 0) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_1_Activity::class.java)
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
                    val intentFrame = Intent(requireContext(), Post_2_Activity::class.java)
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
                    val intentFrame = Intent(requireContext(), Post_3_Activity::class.java)
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
                    val intentFrame = Intent(requireContext(), Post_4_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 4) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_5_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 5) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_6_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 6) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_7_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 7) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_8_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 8) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_9_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 9) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_10_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 10) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_11_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 11) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_12_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 12) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_13_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 13) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_14_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 14) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_15_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 15) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_16_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 16) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_17_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 17) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_18_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 18) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_19_Activity::class.java)
                    startActivity(intentFrame)
                } else {
                    var i = Intent(requireContext(), GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(requireContext(), SubscriptionActivity::class.java)
                startActivity(i)
            }
        } else if (it == 19) {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    val intentFrame = Intent(requireContext(), Post_20_Activity::class.java)
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
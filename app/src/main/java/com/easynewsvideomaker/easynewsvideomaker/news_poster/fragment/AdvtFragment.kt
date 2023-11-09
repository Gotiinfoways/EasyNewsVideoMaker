package com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class AdvtFragment : Fragment() {
    lateinit var advtBinding: FragmentAdvtBinding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var packageType: String? = null
    var advtList = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        advtBinding = FragmentAdvtBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        initView()
        return advtBinding.root
    }


    private fun initView() {

        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    packageType = postSnapshot.child("packageType").value.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        insertImageInList()
        var selectedFragment = "advtFragment"
        var adapter = NewsAdapter(requireContext(),selectedFragment) {

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


//        if (it == 0) {
//            if (packageType == "Golden") {
//                val intent = Intent(requireContext(), Post_1_Activity::class.java)
//                startActivity(intent)
//            } else {
//                var i = Intent(context, GoldenPackageActivity::class.java)
//                startActivity(i)
//            }
//
//        } else if (it == 1) {
//            if (packageType == "Golden") {
//                val intent = Intent(requireContext(), Post_2_Activity::class.java)
//                startActivity(intent)
//            } else {
//                var i = Intent(context, GoldenPackageActivity::class.java)
//                startActivity(i)
//            }
//        } else if (it == 2) {
//            if (packageType == "Golden") {
//                val intent = Intent(requireContext(), Post_3_Activity::class.java)
//                startActivity(intent)
//            } else {
//                var i = Intent(context, GoldenPackageActivity::class.java)
//                startActivity(i)
//            }
//        }

    }
}
package com.easynewsvideomaker.easynewsvideomaker.news_video

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.activity.GoldenPackageActivity
import com.easynewsvideomaker.easynewsvideomaker.activity.SubscriptionActivity
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentHomeBinding
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame10Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame11Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame12Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame13Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame14Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame1Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame2Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame3Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame4Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame5Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame6Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame7Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame8Fragment
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame.VideoFrame9Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.io.File

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var packageType: String? = null
    var userBlock: String? = null

    //initialize root directory
    var rootDir = Environment.getExternalStorageDirectory().path
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
//making sure the download directory exists
        checkAndCreateDirectory("/Easy News Maker/News Video Maker")
        checkAndCreateDirectory("/Easy News Maker/cash")

        intiView()
        return binding.root
    }

    //function to verify if directory exists
    fun checkAndCreateDirectory(dirName: String) {
        val new_dir: File = File(rootDir + dirName)
        if (!new_dir.exists()) {
            new_dir.mkdirs()
        }
    }

    private fun intiView() {


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


        binding.frame1.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    var fragment = VideoFrame1Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, GoldenPackageActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame2.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame2Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame3.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame3Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame4.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame4Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame5.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame5Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame6.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame6Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame7.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame7Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame8.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame8Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame9.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame9Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame10.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame10Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }

        binding.frame11.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden" || packageType == "Silver") {
                    var fragment = VideoFrame11Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame12.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    var fragment = VideoFrame12Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame13.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    var fragment = VideoFrame13Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }
        binding.frame14.setOnClickListener {
            if (userBlock == "unblock") {
                if (packageType == "Golden") {
                    var fragment = VideoFrame14Fragment()
                    setFragment(fragment)
                } else {
                    var i = Intent(context, SubscriptionActivity::class.java)
                    startActivity(i)
                }
            } else {
                var i = Intent(context, SubscriptionActivity::class.java)
                startActivity(i)
            }
        }

    }

    fun setFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()

    }

}



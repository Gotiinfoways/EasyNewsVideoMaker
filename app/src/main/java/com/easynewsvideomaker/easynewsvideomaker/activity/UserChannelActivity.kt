package com.easynewsvideomaker.easynewsvideomaker.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityUserChannelBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserChannelModel
import com.easynewsvideomaker.easynewsvideomaker.modelClass.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserChannelActivity : AppCompatActivity() {

    lateinit var userChannelBinding: ActivityUserChannelBinding


    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    var userId: String? = null   //  id variable  define
    var flag = 0    //  flag variable  define
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userChannelBinding = ActivityUserChannelBinding.inflate(layoutInflater)
        setContentView(userChannelBinding.root)


        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Initialize Firebase Auth
        auth = Firebase.auth

        progressDialog()
        initView()
    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressDialog.setContentView(progressBarBinding.root)

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initView() {

        userChannelBinding.linUserLogo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery_Launcher.launch(intent)
        }

        userChannelBinding.cdSaveBtn.setOnClickListener {
            var channelName = userChannelBinding.edtChannelName.text.toString()
            var repoterName = userChannelBinding.edtRepoterName.text.toString()
            var facebookLink = userChannelBinding.edtFacebook.text.toString()
            var twitterLink = userChannelBinding.edtTwitter.text.toString()
            var instagramLink = userChannelBinding.edtInstagram.text.toString()
            var youtubeLink = userChannelBinding.edtYoutube.text.toString()
            var websiteLink = userChannelBinding.edtWebsite.text.toString()




            if (channelName.isEmpty()) {
                Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_SHORT).show()
            } else if (repoterName.isEmpty()) {
                Toast.makeText(this, "Please Enter Repoter Name", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
//                if (flag == 1) {
                    mDbRef.child("user").child(auth.currentUser!!.uid).child("Channel_data").setValue(
                        UserChannelModel(
                            channelName,
                            repoterName,
                            facebookLink,
                            twitterLink,
                            instagramLink,
                            youtubeLink,
                            websiteLink
                        )
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Record Updated Successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                progressDialog.dismiss()
                                var i = Intent(this, AdminHomeActivity::class.java)
                                startActivity(i)
                            }
                        }.addOnFailureListener {
                            Log.e("TAG", "initView: " + it.message)
                            progressDialog.dismiss()
                        }


//                }
            }

        }

    }

    var gallery_Launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.getResultCode() === RESULT_OK) {
                val data: Intent = result.getData()!!
                val uri = data.data
//                var selectedImageUri = getImagePathFromURI(uri!!)
                userChannelBinding.imgUserLogo.setImageURI(uri)
                userChannelBinding.txtUserLogo.visibility = View.GONE
            }
        })
//    private fun getImagePathFromURI(contentUri: Uri): String? {
//        val projection = arrayOf(MediaStore.Video.Media.DATA)
//        val cursor: Cursor? =
//            requireContext().contentResolver.query(contentUri, projection, null, null, null)
//
//
//        cursor?.use {
//            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            it.moveToFirst()
//            imagePath = it.getString(columnIndex)
//            Log.e("TAG", "getPath:${imagePath} ")
//            return imagePath
//        }
//
//        // If the cursor is null, the query failed
//        return contentUri.path // Fallback to using the URI's path
//    }

}
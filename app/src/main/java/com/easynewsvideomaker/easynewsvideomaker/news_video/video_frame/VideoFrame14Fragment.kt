package com.easynewsvideomaker.easynewsvideomaker.news_video.video_frame

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame14Binding
import com.easynewsvideomaker.easynewsvideomaker.news_video.video_export.VideoExport14Fragment
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
import java.io.FileOutputStream
import java.io.IOException

class VideoFrame14Fragment : Fragment() {

    lateinit var videoFrame14Binding: FragmentVideoFrame14Binding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding

    var convertImagePath: String? = null
    var videoPath: String? = null
    private val PICK_VIDEO_REQUEST = 1

    // this is the default color of the preview box
    private var mDefaultColor = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        videoFrame14Binding = FragmentVideoFrame14Binding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
        initView()
        frameEdit()
        return videoFrame14Binding.root
    }


    private fun frameEdit() {
//        //text scroll Horizontally
        videoFrame14Binding.txtLay1.isSelected = true
//        videoFrame14Binding.txtLay2.isSelected = true


        // channel logo and repoter name set
        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    var repoterName = postSnapshot.child("repoterName").value
                    var channelLogo = postSnapshot.child("channelLogo").value




                    Glide.with(requireContext()).load(channelLogo).placeholder(R.drawable.news_logo)
                        .into(videoFrame14Binding.imgNewsLoge)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //     linMeghalayBox   name text change
        videoFrame14Binding.linMeghalayBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtMeghalayTitle.text.toString()
            var textSub = videoFrame14Binding.txtMeghalaySub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtMeghalayTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtMeghalaySub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linTripuraBox   name text change
        videoFrame14Binding.linTripuraBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtTripuraTitle.text.toString()
            var textSub = videoFrame14Binding.txtTripuraSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtTripuraTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtTripuraSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linNagalandBox   name text change
        videoFrame14Binding.linNagalandBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtNagalandTitle.text.toString()
            var textSub = videoFrame14Binding.txtNagalandSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtNagalandTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtNagalandSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linNatijeBox   name text change
        videoFrame14Binding.linNatijeBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtNatijeTitle.text.toString()
            var textSub = videoFrame14Binding.txtNatijeSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtNatijeTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtNatijeSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linTripuraBox2   name text change
        videoFrame14Binding.linTripuraBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtTripuraTitle2.text.toString()
            var textSub = videoFrame14Binding.txtTripuraSub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtTripuraTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtTripuraSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linBjpBox   name text change
        videoFrame14Binding.linBjpBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtBjpTitle.text.toString()
            var textSub = videoFrame14Binding.txtBjpSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtBjpTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtBjpSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linIpftBox   name text change
        videoFrame14Binding.linIpftBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtIpftTitle.text.toString()
            var textSub = videoFrame14Binding.txtIpftSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtIpftTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtIpftSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linLeftBox   name text change
        videoFrame14Binding.linLeftBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtLeftTitle.text.toString()
            var textSub = videoFrame14Binding.txtLeftSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtLeftTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtLeftSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

       //     linOtherBox   name text change
        videoFrame14Binding.linOthBox.setOnClickListener {
            var textTitle = videoFrame14Binding.txtOthTitle.text.toString()
            var textSub = videoFrame14Binding.txtOthSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtOthTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtOthSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //     linMeghalayBox2   name text change
        videoFrame14Binding.linMeghalayBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtMeghalayTitle2.text.toString()
            var textSub = videoFrame14Binding.txtMeghalaySub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtMeghalayTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtMeghalaySub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //     linBjpBox2   name text change
        videoFrame14Binding.linBjpBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtBjpTitle2.text.toString()
            var textSub = videoFrame14Binding.txtBjpSub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtBjpTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtBjpSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linIpftBox2   name text change
        videoFrame14Binding.linIpftBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtIpftTitle2.text.toString()
            var textSub = videoFrame14Binding.txtIpftSub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtIpftTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtIpftSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linLeftBox2   name text change
        videoFrame14Binding.linLeftBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtLeftTitle2.text.toString()
            var textSub = videoFrame14Binding.txtLeftSub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtLeftTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtLeftSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linOtherBox2   name text change
        videoFrame14Binding.linOthBox2.setOnClickListener {
            var textTitle = videoFrame14Binding.txtOthTitle2.text.toString()
            var textSub = videoFrame14Binding.txtOthSub2.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtOthTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame14Binding.txtOthSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     lay1    name text change
        videoFrame14Binding.txtLay1.setOnClickListener {
            var textTitle = videoFrame14Binding.txtLay1.text.toString()
            var textSub = ""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame14Binding.txtLay1.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
    }

    private fun editeDialog(textTitle: String, textSub: String) {

        editeDialog = Dialog(requireContext())
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(textTitle)
        dialogEditBinding.edtText2.setText(textSub)



        dialogEditBinding.linTextColor.visibility = View.GONE
        dialogEditBinding.linBackgroundColor.visibility = View.GONE




        editeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        editeDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editeDialog.show()
    }

    private fun initView() {

        videoFrame14Binding.linCaptureVideo.setOnClickListener {
            val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(i, 1)
        }
        videoFrame14Binding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(context)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoFrame14Binding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoFrame14Binding.vidView)
        //volume set
//        videoFrame2Binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        videoFrame14Binding.vidView.setMediaController(mediaController);

        videoFrame14Binding.vidView.scaleX = 1.0f
        videoFrame14Binding.vidView.scaleY = 1.0f

        //Video Export
        videoFrame14Binding.cdExploreBtn.setOnClickListener {


            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
//
                var bottomColorText = videoFrame14Binding.txtLay1.text.toString()
//                var bottomTextScroll = videoFrame14Binding.txtLay2.text.toString()
//                // Get the text size of the TextView
//                val centerTextSize = videoFrame14Binding.txtLay1.textSize.toInt()
//                val bottomTextSize = videoFrame14Binding.txtLay2.textSize.toInt()
//
//                var centerColorText = videoFrame14Binding.txtLay1.currentTextColor
//                val centerTextColor = String.format("#%06X", 0xFFFFFF and centerColorText)
//
//
//                var bottomColorText = videoFrame14Binding.txtLay2.currentTextColor
//                val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)
//
//
                val fontPath =
                    getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath
//
                val fragment = VideoExport14Fragment()
                val bundle = Bundle()

                bundle.putString("videoPath", videoPath)
                bundle.putString("convertImagePath", convertImagePath)

                bundle.putString("bottomTextScrollPath", bottomColorText)
                bundle.putString("fontPath", fontPath)

                fragment.arguments = bundle
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragment)
                transaction.addToBackStack(null)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    videoFrame14Binding.vidView.setVideoURI(selectedVideoUri)
                    videoFrame14Binding.vidView.requestFocus()


                    // Start playing the video
                    videoFrame14Binding.vidView.visibility = View.VISIBLE
                    videoFrame14Binding.linVideoBackground.visibility = View.GONE
//                    videoFrame3Binding.linVideoZoom.visibility = View.VISIBLE
//                    videoFrame3Binding.linBtn.visibility = View.VISIBLE
//                    videoFrame14Binding.imgView.visibility = View.INVISIBLE
                    videoFrame14Binding.vidView.start()

//                    videoPath=selectedVideoUri.path
                    var videoPath = getVideoPathFromURI(selectedVideoUri!!)
                    Log.e("TAG", "onActivityResult:${videoPath} ")
                }
            }
        }

    }

    var gallery_Launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.getResultCode() === Activity.RESULT_OK) {
                val data: Intent = result.getData()!!
                val uri = data.data
//                var selectedImageUri = getImagePathFromURI(uri!!)
                videoFrame14Binding.imgNewsLoge.setImageURI(uri)
            }
        })

    // Helper method to get the actual path from a URI
    private fun getVideoPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            requireContext().contentResolver.query(contentUri, projection, null, null, null)

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            it.moveToFirst()
            videoPath = it.getString(columnIndex)
            Log.e("TAG", "getPath:${videoPath} ")
            return videoPath
        }


        // If the cursor is null, the query failed
        return contentUri.path // Fallback to using the URI's path
    }

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

    fun getFileFromAssets(context: Context, fileName: String): File =
        File(context.cacheDir, fileName).also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }

    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        var transparentBitmap = Bitmap.createBitmap(
            videoFrame14Binding.frameView.getWidth(),
            videoFrame14Binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        videoFrame14Binding.frameView.draw(canvas)

        // Save the Bitmap as an image file
        val file = File(Environment.getExternalStorageDirectory(), "transparent_image.png")
        try {
            val outputStream = FileOutputStream(file)
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        convertImagePath = file.absolutePath
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
    }


}
package com.easynewsvideomaker.easynewsvideomaker.fragment.video_frame

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
import android.text.InputType
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
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame13Binding
import com.easynewsvideomaker.easynewsvideomaker.fragment.video_export.VideoExport13Fragment
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

class VideoFrame13Fragment : Fragment() {

    lateinit var videoFrame13Binding: FragmentVideoFrame13Binding
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

        videoFrame13Binding = FragmentVideoFrame13Binding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        initView()
        frameEdit()
        return videoFrame13Binding.root
    }


    private fun frameEdit() {

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
                        .into(videoFrame13Binding.imgNewsLoge)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
         //     Braking News  name text change
        videoFrame13Binding.linBrakingNews.setOnClickListener {
            var textTitle = videoFrame13Binding.txtBraking.text.toString()
            var textSub = videoFrame13Binding.txtNews.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE
            dialogEditBinding.edtText2.inputType = InputType.TYPE_CLASS_TEXT

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtBraking.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtNews.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     Bjp  name text change
        videoFrame13Binding.txtBjp.setOnClickListener {
            var textTitle = videoFrame13Binding.txtBjp.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtBjp.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     congress  name text change
        videoFrame13Binding.txtCongress.setOnClickListener {
            var textTitle = videoFrame13Binding.txtCongress.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtCongress.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     Rjd  name text change
        videoFrame13Binding.txtRjd.setOnClickListener {
            var textTitle = videoFrame13Binding.txtRjd.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtRjd.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     JDU  name text change
        videoFrame13Binding.txtJdu.setOnClickListener {
            var textTitle = videoFrame13Binding.txtJdu.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtJdu.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     AAP  name text change
        videoFrame13Binding.txtAap.setOnClickListener {
            var textTitle = videoFrame13Binding.txtAap.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtAap.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //     OTHER  name text change
        videoFrame13Binding.txtOther.setOnClickListener {
            var textTitle = videoFrame13Binding.txtOther.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtOther.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     BrakingNews  name text change
        videoFrame13Binding.txtBrakingNews.setOnClickListener {
            var textTitle = videoFrame13Binding.txtBrakingNews.text.toString()
            var textSub =""


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtBrakingNews.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linBjpBox   name text change
        videoFrame13Binding.linBjpBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtBjpNdaTitle.text.toString()
            var textSub = videoFrame13Binding.txtBjpNdaSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtBjpNdaTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtBjpNdaSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linCongressBox   name text change
        videoFrame13Binding.linCongressBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtCongressTitle.text.toString()
            var textSub = videoFrame13Binding.txtCongressSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtCongressTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtCongressSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
        //     linRjdBox   name text change
        videoFrame13Binding.linRjdBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtRjdTitle.text.toString()
            var textSub = videoFrame13Binding.txtRjdSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtRjdTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtRjdSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linJdu   name text change
        videoFrame13Binding.linJduBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtJduTitle.text.toString()
            var textSub = videoFrame13Binding.txtJduSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtJduTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtJduSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linAapBox   name text change
        videoFrame13Binding.linAapBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtAapTitle.text.toString()
            var textSub = videoFrame13Binding.txtAapSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtAapTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtAapSub.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     linOtherBox   name text change
        videoFrame13Binding.linOtherBox.setOnClickListener {
            var textTitle = videoFrame13Binding.txtOtherTitle.text.toString()
            var textSub = videoFrame13Binding.txtOtherSub.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame13Binding.txtOtherTitle.text = dialogEditBinding.edtText.text.toString()
                videoFrame13Binding.txtOtherSub.text = dialogEditBinding.edtText2.text.toString()

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

        videoFrame13Binding.linCaptureVideo.setOnClickListener {
            val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(i, 1)
        }
        videoFrame13Binding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(context)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoFrame13Binding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoFrame13Binding.vidView)
        //volume set
//        videoFrame2Binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        videoFrame13Binding.vidView.setMediaController(mediaController);

        videoFrame13Binding.vidView.scaleX = 1.0f
        videoFrame13Binding.vidView.scaleY = 1.0f
        //Video Export
        videoFrame13Binding.cdExploreBtn.setOnClickListener {


            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
//
//                var centerTextScroll = videoFrame13Binding.txtLay1.text.toString()
//                var bottomTextScroll = videoFrame13Binding.txtLay2.text.toString()
//                // Get the text size of the TextView
//                val centerTextSize = videoFrame13Binding.txtLay1.textSize.toInt()
//                val bottomTextSize = videoFrame13Binding.txtLay2.textSize.toInt()
//
//                var centerColorText = videoFrame13Binding.txtLay1.currentTextColor
//                val centerTextColor = String.format("#%06X", 0xFFFFFF and centerColorText)
//
//
//                var bottomColorText = videoFrame13Binding.txtLay2.currentTextColor
//                val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)
//
//
//                val fontPath =
//                    getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath
//
                val fragment = VideoExport13Fragment()
                val bundle = Bundle()

                bundle.putString("videoPath", videoPath)
                bundle.putString("convertImagePath", convertImagePath)

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
                    videoFrame13Binding.vidView.setVideoURI(selectedVideoUri)
                    videoFrame13Binding.vidView.requestFocus()


                    // Start playing the video
                    videoFrame13Binding.vidView.visibility = View.VISIBLE
                    videoFrame13Binding.linVideoBackground.visibility = View.GONE
//                    videoFrame3Binding.linVideoZoom.visibility = View.VISIBLE
//                    videoFrame3Binding.linBtn.visibility = View.VISIBLE
//                    videoFrame13Binding.imgView.visibility = View.INVISIBLE
                    videoFrame13Binding.vidView.start()

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
                videoFrame13Binding.imgNewsLoge.setImageURI(uri)
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
            videoFrame13Binding.frameView.getWidth(),
            videoFrame13Binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        videoFrame13Binding.frameView.draw(canvas)

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
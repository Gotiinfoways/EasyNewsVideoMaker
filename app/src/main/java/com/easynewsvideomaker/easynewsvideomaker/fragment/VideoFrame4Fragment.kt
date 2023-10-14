package com.easynewsvideomaker.easynewsvideomaker.fragment

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
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame4Binding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class VideoFrame4Fragment : Fragment() {

    lateinit var videoFrame4Binding: FragmentVideoFrame4Binding


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
        videoFrame4Binding= FragmentVideoFrame4Binding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        initView()
        frameEdit()
        return  videoFrame4Binding.root
    }


    private fun frameEdit() {
        //text scroll Horizontally
        videoFrame4Binding.txtLay2.isSelected = true


        //set image
        videoFrame4Binding.imgNewsLoge.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery_Launcher.launch(intent)
        }

        //       city name text change
        videoFrame4Binding.txtCityName.setOnClickListener {
            var text = videoFrame4Binding.txtCityName.text.toString()


            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame4Binding.txtCityName.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
//
//        //      Latest Update text change
//        videoFrame4Binding.txtLatestUpdate.setOnClickListener {
//            var text = videoFrame4Binding.txtLatestUpdate.text.toString()
//
//
//            editeDialog(text)
//
//            dialogEditBinding.btnSubmit.setOnClickListener {
//
//
//                videoFrame4Binding.txtLatestUpdate.text = dialogEditBinding.edtText.text.toString()
//
//                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
//                editeDialog.dismiss()
//            }
//        }

        //      txt Layer 1 text change
        videoFrame4Binding.txtLay1.setOnClickListener {
            var text = videoFrame4Binding.txtLay1.text.toString()


            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame4Binding.txtLay1.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //      txt Layer 2 text change
        videoFrame4Binding.txtLay2.setOnClickListener {
            var text = videoFrame4Binding.txtLay2.text.toString()


            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame4Binding.txtLay2.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
    }

    private fun editeDialog(text: String) {

        editeDialog = Dialog(requireContext())
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(text)

        dialogEditBinding.linTextColor.visibility=View.GONE
        dialogEditBinding.linBackgroundColor.visibility=View.GONE




        editeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        editeDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editeDialog.show()
    }
    private fun initView() {

        videoFrame4Binding.linCaptureVideo.setOnClickListener {
            val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(i, 1)
        }
        videoFrame4Binding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(context)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoFrame4Binding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoFrame4Binding.vidView)
        //volume set
//        videoFrame2Binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        videoFrame4Binding.vidView.setMediaController(mediaController);


        //Video Export
        videoFrame4Binding.cdExploreBtn.setOnClickListener {


            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {

                var bottomTextScroll = videoFrame4Binding.txtLay2.text.toString()
                // Get the text size of the TextView
                val bottomTextSize = videoFrame4Binding.txtLay2.textSize.toInt()



                var bottomColorText = videoFrame4Binding.txtLay2.currentTextColor
                val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)


                val fontPath = getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath

                val fragment = VideoExport4_and_5Fragment()
                val bundle = Bundle()

                bundle.putString("videoPath", videoPath)
                bundle.putString("convertImagePath", convertImagePath)
                bundle.putString("bottomTextScrollPath", bottomTextScroll)
                bundle.putInt("bottomTextSize", bottomTextSize)
                bundle.putString("bottomTextColor", bottomTextColor)
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
                    videoFrame4Binding.vidView.setVideoURI(selectedVideoUri)
                    videoFrame4Binding.vidView.requestFocus()


                    // Start playing the video
                    videoFrame4Binding.vidView.visibility = View.VISIBLE
//                    videoFrame3Binding.linVideoZoom.visibility = View.VISIBLE
//                    videoFrame3Binding.linBtn.visibility = View.VISIBLE
                    videoFrame4Binding.imgView.visibility = View.INVISIBLE
                    videoFrame4Binding.vidView.start()

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
                videoFrame4Binding.imgNewsLoge.setImageURI(uri)
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
            videoFrame4Binding.frameView.getWidth(),
            videoFrame4Binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        videoFrame4Binding.frameView.draw(canvas)

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
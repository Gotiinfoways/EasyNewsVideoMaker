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
import android.util.Log
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame11Binding
import com.easynewsvideomaker.easynewsvideomaker.fragment.video_export.VideoExport11Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoFrame11Fragment : Fragment() {
lateinit var videoFrame11Binding: FragmentVideoFrame11Binding

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

    videoFrame11Binding = FragmentVideoFrame11Binding.inflate(layoutInflater, container, false)
    // Inflate the layout for this fragment

    initView()
    frameEdit()
    return videoFrame11Binding.root
}


private fun frameEdit() {
    //text scroll Horizontally
    videoFrame11Binding.txtLay1.isSelected = true
    videoFrame11Binding.txtLay2.isSelected = true
    videoFrame11Binding.txtLay3.isSelected = true


    //set image
    videoFrame11Binding.imgNewsLoge.setOnClickListener {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        gallery_Launcher.launch(intent)
    }

    //       city name text change
    videoFrame11Binding.txtCityName.setOnClickListener {
        var text = videoFrame11Binding.txtCityName.text.toString()


        editeDialog(text)

        dialogEditBinding.btnSubmit.setOnClickListener {


            videoFrame11Binding.txtCityName.text = dialogEditBinding.edtText.text.toString()

            Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
            editeDialog.dismiss()
        }
    }

        //      Live text change
        videoFrame11Binding.txtLive.setOnClickListener {
            var text = videoFrame11Binding.txtLive.text.toString()


            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame11Binding.txtLive.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

    //      txt Layer 1 text change
    videoFrame11Binding.txtLay1.setOnClickListener {
        var text = videoFrame11Binding.txtLay1.text.toString()


        editeDialog(text)

        dialogEditBinding.btnSubmit.setOnClickListener {


            videoFrame11Binding.txtLay1.text = dialogEditBinding.edtText.text.toString()

            Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
            editeDialog.dismiss()
        }
    }

    //      txt Layer 2 text change
    videoFrame11Binding.txtLay2.setOnClickListener {
        var text = videoFrame11Binding.txtLay2.text.toString()


        editeDialog(text)

        dialogEditBinding.btnSubmit.setOnClickListener {


            videoFrame11Binding.txtLay2.text = dialogEditBinding.edtText.text.toString()

            Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
            editeDialog.dismiss()
        }
    }
    //      txt Layer 3 text change
    videoFrame11Binding.txtLay3.setOnClickListener {
        var text = videoFrame11Binding.txtLay3.text.toString()


        editeDialog(text)

        dialogEditBinding.btnSubmit.setOnClickListener {


            videoFrame11Binding.txtLay3.text = dialogEditBinding.edtText.text.toString()

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

    videoFrame11Binding.linCaptureVideo.setOnClickListener {
        val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(i, 1)
    }
    videoFrame11Binding.linImportVideo.setOnClickListener {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, PICK_VIDEO_REQUEST)
    }

    // creating object of
    // media controller class
    var mediaController = MediaController(context)
    // sets the anchor view
    // anchor view for the videoView
    mediaController.setAnchorView(videoFrame11Binding.vidView)
    // sets the media player to the videoView
    mediaController.setMediaPlayer(videoFrame11Binding.vidView)
    //volume set
//        videoFrame2Binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
    // sets the media controller to the videoView
    videoFrame11Binding.vidView.setMediaController(mediaController);

    videoFrame11Binding.vidView.scaleX = 1.4f
    videoFrame11Binding.vidView.scaleY = 1.4f
    //Video Export
    videoFrame11Binding.cdExploreBtn.setOnClickListener {


        saveFrameLayoutAsImage()
        if (videoPath == null) {

            Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

        } else {

            var topTextScroll = videoFrame11Binding.txtLay1.text.toString()
            var centerTextScroll = videoFrame11Binding.txtLay2.text.toString()
            var bottomTextScroll = videoFrame11Binding.txtLay3.text.toString()
            // Get the text size of the TextView
//                val centerTextSize = videoFrame11Binding.txtLay1.textSize.toInt()
//                val bottomTextSize = videoFrame11Binding.txtLay2.textSize.toInt()
            var topColorText = videoFrame11Binding.txtLay1.currentTextColor
            val topTextColor = String.format("#%06X", 0xFFFFFF and topColorText)

            var centerColorText = videoFrame11Binding.txtLay2.currentTextColor
            val centerTextColor = String.format("#%06X", 0xFFFFFF and centerColorText)


            var bottomColorText = videoFrame11Binding.txtLay3.currentTextColor
            val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)


            val fontPath =
                getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath

            val fragment = VideoExport11Fragment()
            val bundle = Bundle()

            bundle.putString("videoPath", videoPath)
            bundle.putString("convertImagePath", convertImagePath)
            bundle.putString("topTextScrollPath", topTextScroll)
            bundle.putString("topTextColor", topTextColor)
            bundle.putString("centerTextScrollPath", centerTextScroll)
            bundle.putString("centerTextColor", centerTextColor)
            bundle.putString("bottomTextScrollPath", bottomTextScroll)
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
                videoFrame11Binding.vidView.setVideoURI(selectedVideoUri)
                videoFrame11Binding.vidView.requestFocus()


                // Start playing the video
                videoFrame11Binding.vidView.visibility = View.VISIBLE
//                    videoFrame3Binding.linVideoZoom.visibility = View.VISIBLE
//                    videoFrame3Binding.linBtn.visibility = View.VISIBLE
                videoFrame11Binding.imgView.visibility = View.INVISIBLE
                videoFrame11Binding.vidView.start()

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
            videoFrame11Binding.imgNewsLoge.setImageURI(uri)
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
        videoFrame11Binding.frameView.getWidth(),
        videoFrame11Binding.frameView.getHeight(),
        Bitmap.Config.ARGB_8888
    )
    transparentBitmap.eraseColor(Color.TRANSPARENT)

    // Capture the content of the FrameLayout
    val canvas = Canvas(transparentBitmap)
    videoFrame11Binding.frameView.draw(canvas)

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
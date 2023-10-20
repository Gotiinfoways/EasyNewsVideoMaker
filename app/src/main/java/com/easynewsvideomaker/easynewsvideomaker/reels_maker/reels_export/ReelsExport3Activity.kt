package com.easynewsvideomaker.easynewsvideomaker.reels_maker.reels_export

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityReelsExport3Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogWarningBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DownloadProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.easynewsvideomaker.easynewsvideomaker.reels_maker.ReelsMakerActivity

class ReelsExport3Activity : AppCompatActivity() {

    lateinit var exportBinding: ActivityReelsExport3Binding
    lateinit var selectedVideoUri: Uri

    private val VIDEO_PERMISSION_REQUEST = 101

    lateinit var ffmpegQueryExtension: FFmpegQueryExtension

    var videoPath: String? = null
    var convertImagePath: String? = null
    var textScroll: String? = null
    var textSize = 0
    var textColor: String? = null

    var bottomTextScroll: String? = null
    var bottomTextSize = 0
    var bottomTextColor: String? = null
    lateinit var downloadProgressDialog: Dialog
    lateinit var progressBarBinding: DownloadProgressBarBinding
    private var primaryProgressStatus = 0
    private val handler = Handler()

    val DIALOG_DOWNLOAD_PROGRESS = 0


    var buttonClick = 0

    var fontPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exportBinding = ActivityReelsExport3Binding.inflate(layoutInflater)
        setContentView(exportBinding.root)


        ffmpegQueryExtension = FFmpegQueryExtension()


        progressDialog()
        initView()
    }

    private fun progressDialog() {
        downloadProgressDialog = Dialog(this)
        progressBarBinding = DownloadProgressBarBinding.inflate(layoutInflater)
        downloadProgressDialog.setContentView(progressBarBinding.root)
        primaryProgressStatus = progressBarBinding.progressBar.progress

        downloadProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        downloadProgressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        downloadProgressDialog.setCancelable(false)
    }

    private fun initView() {



        //text scroll Horizontally
//        exportBinding.txtLay1.isSelected = true

        videoPath = intent?.getStringExtra("videoPath")
        convertImagePath = intent?.getStringExtra("convertImagePath")

//        textScroll = intent?.getStringExtra("textScroll")
//        textColor = intent?.getStringExtra("textColor")
//
//        fontPath = intent?.getStringExtra("fontPath")!!

        val myBitmap = BitmapFactory.decodeFile(convertImagePath)
        exportBinding.imgExport.setImageBitmap(myBitmap)

        var videoUri = Uri.parse(videoPath)
        exportBinding.vidExport.setVideoURI(videoUri)
        exportBinding.vidExport.requestFocus()

        var mediaController = MediaController(this)
        mediaController.setAnchorView(exportBinding.vidExport)
        mediaController.setMediaPlayer(exportBinding.vidExport)
        exportBinding.vidExport.setMediaController(mediaController)
        exportBinding.vidExport.scaleX = 1.5f
        exportBinding.vidExport.scaleY = 1.5f
        exportBinding.vidExport.start()


//        exportBinding.txtLay1.setText(textScroll)

        exportBinding.linDownload.setOnClickListener {
            buttonClick = 1
            val dialog = Dialog(this)
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                downloadProgressDialog.show()


                addReelsMakerFun(fileName)
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        exportBinding.cdDoneBtn.setOnClickListener {

            if (buttonClick == 1) {
                var i = Intent(this, ReelsMakerActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "Please First Save Videos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addReelsMakerFun(fileName: String) {

        var outputPath =
            Environment.getExternalStorageDirectory().path + "/Easy News Maker/Reels Maker/$fileName.mp4"

        var inputVideo = videoPath!!

        var imageInput = convertImagePath!!


        var textInput = textScroll
        var textInputeColor = textColor

        //text size auto change by video wigth and hight
        val textInputeSize = calculateFontSize(this, inputVideo)
//
        val yPosition: Int = calculateYCenterPosition(this, inputVideo)
        // Get the video's width and height
        val videoWidth = getVideoWidth(inputVideo)
        val videoHeight = getVideoHeight(inputVideo)

        var fontPath = fontPath

        val query = ffmpegQueryExtension.addFrame2ReelsMakerFun(
            inputVideo,
            imageInput,
            videoWidth,
            videoHeight,
            outputPath,
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {
//                exportBinding.txtOutputPath.visibility = View.VISIBLE
//                exportBinding.txtOutputPath.text = logMessage.text
            }

            override fun success() {
                exportBinding.txtOutputPath.visibility = View.VISIBLE
                exportBinding.txtOutputPath.text =
                    String.format(getString(R.string.output_path), outputPath)
                downloadProgressDialog.dismiss()
                Toast.makeText(
                    this@ReelsExport3Activity,
                    "Video Download Success",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun cancel() {

                downloadProgressDialog.dismiss()
                Toast.makeText(
                    this@ReelsExport3Activity,
                    "Video Download Cancel",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun failed() {

                downloadProgressDialog.dismiss()
                Toast.makeText(this@ReelsExport3Activity, "Video Download Fail", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun calculateFontSize(context: Context, videoPath: String): Int {
        // Calculate font size based on video dimensions or other factors.
        val videoHeight = getVideoHeight(videoPath)
        val videoWidth = getVideoWidth(videoPath)

        // You can implement your own logic to determine the font size based on height and width.
        // For example, you might want the font size to be a percentage of the video height or width.
        return (0.07 * Math.min(videoHeight, videoWidth)).toInt()
    }

    private fun calculateYCenterPosition(
        context: Context,
        videoPath: String
    ): Int {
        // Calculate the Y position based on video dimensions or other factors.
        val videoHeight = getVideoHeight(videoPath)

        // You can implement your own logic to determine the Y position based on height.
        // For example, you might want the Y position to be a percentage of the video height.
        return (0.86 * videoHeight).toInt()
    }

    // Function to get the video's width
    private fun getVideoWidth(videoPath: String): Int {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoPath)
        val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        return width?.toInt() ?: 0 // Error handling
    }

    // Function to get the video's height
    private fun getVideoHeight(videoPath: String): Int {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoPath)
        val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        return height?.toInt() ?: 0 // Error handling
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == VIDEO_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initiate download

            } else {
                // Permission denied
                // Handle the case where the user denied permission
            }
        }
    }


    override fun onBackPressed() {
        // Add your custom back press handling logic here.
        // Return true if you consume the back press, false otherwise.
        // For example:

        if (buttonClick == 0) {
            // Handle the back press and return true
            warningDialog()

        }else{
            super.onBackPressed()
        }
        // If you don't want to consume the back press, return false
        return
    }

    private fun warningDialog() {

        var dialog = Dialog(this)
        var warningBinding = DialogWarningBinding.inflate(layoutInflater)
        dialog.setContentView(warningBinding.root)

        warningBinding.btnOk.setOnClickListener {
            var i = Intent(this, ReelsMakerActivity::class.java)
            startActivity(i)
            finish()
            dialog.dismiss()

        }
        warningBinding.btnCansel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }


}
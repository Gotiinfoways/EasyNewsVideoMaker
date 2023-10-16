package com.easynewsvideomaker.easynewsvideomaker.fragment

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DownloadProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoExport14Binding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage

class VideoExport14Fragment : Fragment() {

    lateinit var exportBinding: FragmentVideoExport14Binding

    lateinit var selectedVideoUri: Uri

    private val VIDEO_PERMISSION_REQUEST = 101

    lateinit var ffmpegQueryExtension: FFmpegQueryExtension

    var videoPath: String? = null
    var convertImagePath: String? = null

//    var topTextScroll: String? = null
//    var topTextColor: String? = null
//
//    var top2TextScroll: String? = null
//    var top2TextColor: String? = null
//
//    var centerTextScroll: String? = null
//    var centerTextColor: String? = null
//    var centerTextPostionX = 0f
//    var centerTextPostionY = 0f

    var bottomTextScroll: String? = null

    //    var bottomTextColor: String? = null
    lateinit var downloadProgressDialog: Dialog
    lateinit var progressBarBinding: DownloadProgressBarBinding
    private var primaryProgressStatus = 0
    private val handler = Handler()

    val DIALOG_DOWNLOAD_PROGRESS = 0

    var buttonClick = 0

    //
    var fontPath = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exportBinding = FragmentVideoExport14Binding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        ffmpegQueryExtension = FFmpegQueryExtension()


        progressDialog()
        initView()
        return exportBinding.root
    }


    private fun progressDialog() {
        downloadProgressDialog = Dialog(requireContext())
        progressBarBinding = DownloadProgressBarBinding.inflate(layoutInflater)
        downloadProgressDialog.setContentView(progressBarBinding.root)
        primaryProgressStatus = progressBarBinding.progressBar.progress


        //here’s the download code
//
//        Thread(Runnable {
//            // this loop will run until the value of i becomes 99
//            while (primaryProgressStatus < 100) {
//                primaryProgressStatus += 1
//                // Update the progress bar and display the current value
//                handler.post(Runnable {
//                    progressBarBinding.progressBar.progress = primaryProgressStatus
//                    // setting current progress to the textview
////                    progressBarBinding.txtProgress.text = i.toString() + "/" + progressBarBinding.progressBar.max
//                    progressBarBinding.txtProgress.text = "$primaryProgressStatus %"
//                })
//                try {
//                    Thread.sleep(100)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//
//
//        }).start()
        downloadProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        downloadProgressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        downloadProgressDialog.setCancelable(false)
    }

    private fun initView() {


        //text scroll Horizontally
        exportBinding.txtLay1.isSelected = true
//        exportBinding.txtLay2.isSelected = true
//        exportBinding.txtLay3.isSelected = true

        videoPath = arguments?.getString("videoPath")
        convertImagePath = arguments?.getString("convertImagePath")

//
        bottomTextScroll = arguments?.getString("bottomTextScrollPath")
//        bottomTextColor = arguments?.getString("bottomTextColor")
        fontPath = arguments?.getString("fontPath")!!

        val myBitmap = BitmapFactory.decodeFile(convertImagePath)
        exportBinding.imgExport.setImageBitmap(myBitmap)

        var videoUri = Uri.parse(videoPath)
        exportBinding.vidExport.setVideoURI(videoUri)
        exportBinding.vidExport.requestFocus()

        var mediaController = MediaController(context)
        mediaController.setAnchorView(exportBinding.vidExport)
        mediaController.setMediaPlayer(exportBinding.vidExport)
        exportBinding.vidExport.setMediaController(mediaController);
        exportBinding.vidExport.start()


        exportBinding.txtLay1.setText(bottomTextScroll)

        exportBinding.linDownload.setOnClickListener {
//            downloadVideo()
            buttonClick = 1
            val dialog = Dialog(requireContext())
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                downloadProgressDialog.show()

//                addImageOnVideo(fileName)
                addVideoEditFun(fileName)
//                mixVideo(fileName)
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
                val fragment = HomeFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragment)
                transaction.addToBackStack(null)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            } else {
                Toast.makeText(context, "Please First Save Videos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addVideoEditFun(fileName: String) {


        var outputPath =
            Environment.getExternalStorageDirectory().path + "/Easy News Maker/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvInputPathImage = convertImagePath!!

        //text size auto change by video wigth and hight
        val textInputeSize3 = calculateText3FontSize(requireContext(), tvInputPathVideo)


        val yBottomPosition: Int = calculateYBottomPosition(requireContext(), tvInputPathVideo)
        // Get the video's width and height
        val videoWidth = getVideoWidth(tvInputPathVideo)
        val videoHeight = getVideoHeight(tvInputPathVideo)
        val query = ffmpegQueryExtension.addFrame14VideoEditFun(
            tvInputPathVideo,
            tvInputPathImage,
            outputPath,
            videoWidth, videoHeight, bottomTextScroll, yBottomPosition, textInputeSize3, fontPath
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
                Toast.makeText(context, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                downloadProgressDialog.dismiss()
                Toast.makeText(context, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                downloadProgressDialog.dismiss()
                Toast.makeText(context, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun calculateText3FontSize(context: Context, videoPath: String): Int {
        // Calculate font size based on video dimensions or other factors.
        val videoHeight = getVideoHeight(videoPath)
        val videoWidth = getVideoWidth(videoPath)

        // You can implement your own logic to determine the font size based on height and width.
        // For example, you might want the font size to be a percentage of the video height or width.
        return (0.07 * Math.min(videoHeight, videoWidth)).toInt()
    }


    private fun calculateYBottomPosition(
        context: Context,
        videoPath: String
    ): Int {
        // Calculate the Y position based on video dimensions or other factors.
        val videoHeight = getVideoHeight(videoPath)

        // You can implement your own logic to determine the Y position based on height.
        // For example, you might want the Y position to be a percentage of the video height.
        return (0.92 * videoHeight).toInt()
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


//    fun onBackPressed(): Boolean {
//        // Add your custom back press handling logic here.
//        // Return true if you consume the back press, false otherwise.
//        // For example:
//
//        if (buttonClick == 0) {
//            // Handle the back press and return true
////            warningDialog()
//
//            var dialog = Dialog(requireContext())
//            var warningBinding = DialogWarningBinding.inflate(layoutInflater)
//            dialog.setContentView(warningBinding.root)
//
//            warningBinding.btnOk.setOnClickListener {
//                var i = Intent(requireContext(), HomeActivity::class.java)
//                requireContext().startActivity(i)
//                fragmentManager?.beginTransaction()?.remove(this)?.commit()
//
//
//
//                dialog.dismiss()
//
//
//            }
//            warningBinding.btnCansel.setOnClickListener {
//                dialog.dismiss()
//            }
//
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.window?.setLayout(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            dialog.show()
//            return true
//
//        }
//        // If you don't want to consume the back press, return false
//        return false
//    }
//
//    private fun warningDialog() {
//
//    }

}

package com.easynewsvideomaker.easynewsvideomaker.fragment

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoExportBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import java.io.File
import java.io.IOException


class VideoExportFragment : Fragment() {

    lateinit var exportBinding: FragmentVideoExportBinding

    lateinit var selectedVideoUri: Uri

    private val VIDEO_PERMISSION_REQUEST = 101

    lateinit var ffmpegQueryExtension: FFmpegQueryExtension

    var videoPath: String? = null
    var convertImagePath: String? = null

    lateinit var progressDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exportBinding = FragmentVideoExportBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        ffmpegQueryExtension = FFmpegQueryExtension()
        progressDialog()
        initView()
        return exportBinding.root
    }

    private fun progressDialog() {
        progressDialog = Dialog(requireContext())
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressDialog.setContentView(progressBarBinding.root)

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initView() {

        videoPath = arguments?.getString("videoPath")
        convertImagePath = arguments?.getString("convertImagePath")


        val myBitmap = BitmapFactory.decodeFile(convertImagePath)
        exportBinding.imgExport.setImageBitmap(myBitmap)

        var videoUri = Uri.parse(videoPath)
        exportBinding.vidExport.setVideoURI(videoUri)
        exportBinding.vidExport.requestFocus()

        var mediaController = MediaController(context)
        mediaController.setAnchorView( exportBinding.vidExport)
        mediaController.setMediaPlayer( exportBinding.vidExport)
        exportBinding.vidExport.setMediaController(mediaController);
        exportBinding.vidExport.start()

        exportBinding.linDownload.setOnClickListener {
//            downloadVideo()
            val dialog = Dialog(requireContext())
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                progressDialog.show()
//
                addImageOnVideo(fileName)
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


            val fragment = HomeFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }


    private fun addImageOnVideo(fileName: String) {


        var outputPathBrakingNews1 =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvInputPathImage = convertImagePath!!


        val query = ffmpegQueryExtension.addImageOnVideo(
            tvInputPathVideo,
            tvInputPathImage,

            outputPathBrakingNews1
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {

            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(context, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(context, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(context, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun downloadVideo() {
        if (exportBinding.vidExport != null) {
            val videoUri = Uri.parse(selectedVideoUri.toString())
            val fileName = "downloaded_video_sr.mp4"  //save video name
            val downloadPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val outputFile = File(downloadPath, fileName)

            exportBinding.vidExport.stopPlayback()
            exportBinding.vidExport.setVideoURI(videoUri)
            exportBinding.vidExport.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.setOnSeekCompleteListener {
                    mediaPlayer.start()
                    startDownload(videoUri, outputFile)
                }
                mediaPlayer.seekTo(0)
            }
        }
    }

    private fun startDownload(videoUri: Uri, outputFile: File) {
        try {
            val inputStream = requireActivity().contentResolver.openInputStream(videoUri)
            val outputStream = outputFile.outputStream()
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
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
                downloadVideo()
            } else {
                // Permission denied
                // Handle the case where the user denied permission
            }
        }
    }
}
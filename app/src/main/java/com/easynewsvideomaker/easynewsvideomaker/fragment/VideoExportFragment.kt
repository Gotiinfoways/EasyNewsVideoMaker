package com.easynewsvideomaker.easynewsvideomaker.fragment

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoExportBinding
import java.io.File
import java.io.IOException

class VideoExportFragment : Fragment() {

    lateinit var exportBinding: FragmentVideoExportBinding

    lateinit var selectedVideoUri: Uri

    private val VIDEO_PERMISSION_REQUEST = 101
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exportBinding=FragmentVideoExportBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        return exportBinding.root
    }

    private fun initView() {
        var videoUri= Uri.parse("")
        exportBinding.vidExport.setVideoURI(videoUri)
        exportBinding.vidExport.requestFocus()

        exportBinding.linDownload.setOnClickListener {
            downloadVideo()
            Toast.makeText(context, "Video Download", Toast.LENGTH_SHORT).show()
        }

        // Start playing the video

//        exportBinding.vidExport.start()
    }

    private fun downloadVideo() {
        if (  exportBinding.vidExport!= null) {
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
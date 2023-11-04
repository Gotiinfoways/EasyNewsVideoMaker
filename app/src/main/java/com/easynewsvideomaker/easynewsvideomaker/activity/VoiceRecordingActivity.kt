package com.easynewsvideomaker.easynewsvideomaker.activity

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityVoiceRecordingBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.SaveAudioDialogboxBinding
import com.easynewsvideomaker.easynewsvideomaker.fragment.video_export.VideoExport1Fragment
import com.easynewsvideomaker.easynewsvideomaker.fragment.video_frame.VideoFrame1Fragment
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class VoiceRecordingActivity : AppCompatActivity() {

    lateinit var voiceRecordingBinding: ActivityVoiceRecordingBinding

    private var fileName: String = ""
    private var outputFile: File? = null

    private var recorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var recordingStartTime: Long = 0
    var record = 0
    private val handler = Handler(Looper.getMainLooper())


    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
//            grantResults[0] == PackageManager.PERMISSION_GRANTED
//        } else {
//            false
//        }
//        if (!permissionToRecordAccepted) finish()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        voiceRecordingBinding = ActivityVoiceRecordingBinding.inflate(layoutInflater)
        setContentView(voiceRecordingBinding.root)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir!!.absolutePath}/audiorecordtest.3gp"

//        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        // Request necessary permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            1
        )
        initView()
    }

    private fun initView() {

        voiceRecordingBinding.imgRecordPlayButton.setOnClickListener {
            if (record == 0) {

                if (!isRecording) {
                    startRecording()

                } else {

                    stopRecording()

                }
                isRecording = !isRecording
            } else if (record == 1) {

                if (!isRecording) {
                    startPlaying()
                }
            }
        }

        voiceRecordingBinding.btnAudioDelete.setOnClickListener {
            mediaPlayer!!.release()

            val fragment = VideoFrame1Fragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
        voiceRecordingBinding.btnAudioSave.setOnClickListener {
            onSaveAudio()
        }
    }

    private fun startRecording() {

        voiceRecordingBinding.txtRecordPlayTitle.text = "Stop Recording"
        voiceRecordingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_stop_recoding)

        recorder = MediaRecorder()
        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val filePath = "${Environment.getExternalStorageDirectory()}/audio_record.3gp"
        recorder?.setOutputFile(filePath)

        try {
            recorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        recorder?.start()

        voiceRecordingBinding.txtRecodingStatus.text = "Recording Started"
        recordingStartTime = System.currentTimeMillis()

        handler.post(updateDuration)
    }

    private fun stopRecording() {
        record = 1

        voiceRecordingBinding.txtRecordPlayTitle.text = "Play Audio"
        voiceRecordingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_play_recoding)

        recorder?.stop()

        voiceRecordingBinding.txtRecodingStatus.text = "Recording Stopped"
        recorder?.release()
        recorder = null
        handler.removeCallbacks(updateDuration)
    }

    private val updateDuration = object : Runnable {
        override fun run() {

            val currentTime = System.currentTimeMillis()
            val elapsedMillis = currentTime - recordingStartTime
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis)
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60

            voiceRecordingBinding.txtDuration.text =
                "Duration: $minutes:${String.format("%02d", remainingSeconds)}"

            handler.postDelayed(this, 1000)
        }
    }


    private fun startPlaying() {

        voiceRecordingBinding.txtRecordPlayTitle.text = "Stop Audio"
        voiceRecordingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_pause_recoding)

        voiceRecordingBinding.txtRecodingStatus.text = "Recording Started Playing"

        voiceRecordingBinding.seekBar.visibility = View.VISIBLE
        voiceRecordingBinding.linSaveAndCancel.visibility = View.VISIBLE

        mediaPlayer = MediaPlayer()
        fileName = "${Environment.getExternalStorageDirectory()}/audio_record.3gp"

        try {
            mediaPlayer?.setDataSource(fileName)
            mediaPlayer?.prepare()
            mediaPlayer?.start()

            updateSeekBar()
            voiceRecordingBinding.txtRecordPlayTitle.text = "Stop Audio"


        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.setOnCompletionListener {
            stopPlaying()

        }


        voiceRecordingBinding.seekBar.max = mediaPlayer!!.duration

//        mediaPlayer.setOnCompletionListener {
//            playButton.text = "Play"
//        }

        voiceRecordingBinding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun stopPlaying() {

        voiceRecordingBinding.txtRecordPlayTitle.text = "Play Audio"
        voiceRecordingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_play_recoding)

        voiceRecordingBinding.txtRecodingStatus.text = "Recording Play Stopped"
        mediaPlayer?.release()
        mediaPlayer = null
    }


    private fun updateSeekBar() {
        if (mediaPlayer != null) { // Check if mediaPlayer is not null
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) { // Check mediaPlayer is still not null
                        voiceRecordingBinding.seekBar.progress = mediaPlayer!!.currentPosition
                        updateDurationText(mediaPlayer!!.currentPosition)
                        if (mediaPlayer!!.isPlaying) {
                            handler.postDelayed(this, 1000)
                        }
                    }
                }
            }, 0)
        }
    }

    private fun updateDurationText(duration: Int) {
        val minutes = duration / 60000
        val seconds = (duration % 60000) / 1000
        voiceRecordingBinding.txtDuration.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun onSaveAudio() {
        val dialog = Dialog(this)
        val dialogBinding: SaveAudioDialogboxBinding =
            SaveAudioDialogboxBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val edtfilename = dialog.findViewById<EditText>(R.id.edtfilename)
        val btnaudiosave = dialog.findViewById<AppCompatButton>(R.id.btnaudiosave)
        val btnaudiocancel = dialog.findViewById<AppCompatButton>(R.id.btnaudiocancel)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        btnaudiosave.setOnClickListener {
            val audioName = edtfilename.text.toString().trim()
            if (audioName.isNotEmpty()) {
                val outputFileName = "$audioName.3gp"
                val outputFile = File(externalCacheDir, outputFileName)

                if (outputFile.exists()) {
                    Toast.makeText(
                        this,
                        "File with the same name already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    try {
                        // Copy the recorded audio file to the new location with the desired name
                        val inputFile = File(fileName) // Define fileName properly
                        if (inputFile.exists()) {
                            outputFile.createNewFile()
                            outputFile.outputStream().use { output ->
                                inputFile.inputStream().copyTo(output)
                            }


                            // The code related to Intents can be used here if needed.

                            val fragment = VideoFrame1Fragment()
//                            val bundle = Bundle()
//                            bundle.putString("audioFilePath", outputFile.absolutePath)
//                            bundle.putString("audioFileName", audioName)
//
//                            fragment.arguments = bundle
                            val transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.container, fragment)
                            transaction.addToBackStack(null)
                            transaction.commit()

                            Toast.makeText(
                                this,
                                "Audio saved as: $outputFileName",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()


                        } else {
                            Toast.makeText(this, "Source audio file not found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(this, "Error saving audio", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a valid file name", Toast.LENGTH_SHORT).show()
            }
        }

        btnaudiocancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
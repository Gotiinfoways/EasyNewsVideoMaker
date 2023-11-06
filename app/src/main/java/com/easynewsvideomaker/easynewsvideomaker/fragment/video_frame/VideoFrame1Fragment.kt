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
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogAudioEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogRecordingBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DownloadProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame1Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.SaveAudioDialogboxBinding
import com.easynewsvideomaker.easynewsvideomaker.fragment.video_export.VideoExport1Fragment
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.github.guilhe.views.SeekBarRangedView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class VideoFrame1Fragment : Fragment() {


    lateinit var displayBinding: FragmentVideoFrame1Binding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding

    private var imagePath: String? = null

    var convertImagePath: String? = null
    var videoPath: String? = null
    private val PICK_VIDEO_REQUEST = 1

    // this is the default color of the preview box
    private var mDefaultColor = 0

    // creating a variable for media recorder object class.
    private var mRecorder: MediaRecorder? = null

    // creating a variable for mediaplayer class
    private var mPlayer: MediaPlayer? = null

    // string variable is created for storing a file name
    private var mFileName: String? = null

    lateinit var recodingBinding: DialogRecordingBinding
    lateinit var recodingDialog: Dialog

    private var fileName: String = ""
    private var outputFile: File? = null

    private var recorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var isPlaying = false
    private var recordingStartTime: Long = 0
    var record = 0


    private var minSeekValue: Float = 0F
    private var maxSeekValue: Float = 0F

    var audioFilePath: String? = null
    private val handler = Handler(Looper.getMainLooper())


    lateinit var ffmpegQueryExtension: FFmpegQueryExtension


    lateinit var downloadProgressDialog: Dialog

    lateinit var progressBarBinding: DownloadProgressBarBinding
    var height: Int? = 0
    var width: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        displayBinding = FragmentVideoFrame1Binding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment


        ffmpegQueryExtension = FFmpegQueryExtension()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        progressDialog()
        initView()
        frameEdit()
        return displayBinding.root

    }

    private fun progressDialog() {
        downloadProgressDialog = Dialog(requireContext())
        progressBarBinding = DownloadProgressBarBinding.inflate(layoutInflater)
        downloadProgressDialog.setContentView(progressBarBinding.root)
//        primaryProgressStatus = progressBarBinding.progressBar.progress


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


    private fun frameEdit() {
        //text scroll Horizontally
        displayBinding.txtLay2.isSelected = true
        displayBinding.txtLay3.isSelected = true

        // channel logo and repoter name set
        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    var repoterName = postSnapshot.child("repoterName").value
                    var channelLogo = postSnapshot.child("channelLogo").value



                    displayBinding.txtLayRepoterName.text = repoterName.toString()

                    Glide.with(requireContext()).load(channelLogo).placeholder(R.drawable.news_logo)
                        .into(displayBinding.imgNewsLoge)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        displayBinding.linBreakingNews.setOnClickListener {
            val colorPickerDialogue = AmbilWarnaDialog(context, mDefaultColor,
                object : OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog) {

                    }

                    override fun onOk(dialog: AmbilWarnaDialog, color: Int) {

                        mDefaultColor = color


                        displayBinding.linBreakingNews.setBackgroundColor(mDefaultColor)
                    }
                })
            colorPickerDialogue.show()
        }

        displayBinding.linBreaking.setOnClickListener {
            var text = displayBinding.txtBreaking.text.toString()

            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linBreaking.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtBreaking.currentTextColor

            editeDialog(text, backgroundColor, textColor)



            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtBreaking.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtBreaking.setTextColor(colorText)
                displayBinding.linBreaking.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }


        }

        //        first news text change
        displayBinding.linNews.setOnClickListener {
            var text = displayBinding.txtNews.text.toString()

            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linNews.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtNews.currentTextColor

            editeDialog(text, backgroundColor, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtNews.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtNews.setTextColor(colorText)
                displayBinding.linNews.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }


        }

        //       city name text change
        displayBinding.txtCityName.setOnClickListener {
            var text = displayBinding.txtCityName.text.toString()

            // Get the background of the CardView
            val colorInt = displayBinding.cdCityName.cardBackgroundColor
            // Get the color int from the ColorStateList
            val backgroundColor = colorInt.defaultColor

            var textColor = displayBinding.txtCityName.currentTextColor


            editeDialog(text, backgroundColor, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtCityName.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtCityName.setTextColor(colorText)
                displayBinding.cdCityName.setCardBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //        second Breaking text change
        displayBinding.linBreaking2.setOnClickListener {
            var text = displayBinding.txtBreaking2.text.toString()

            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linBreaking2.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtBreaking2.currentTextColor

            editeDialog(text, backgroundColor, textColor)
            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtBreaking2.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtBreaking2.setTextColor(colorText)
                displayBinding.linBreaking2.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }

        }

        //        second news text change
        displayBinding.linNews2.setOnClickListener {
            var text = displayBinding.txtNews2.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linNews2.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtNews2.currentTextColor

            editeDialog(text, backgroundColor, textColor)
            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtNews2.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtNews2.setTextColor(colorText)
                displayBinding.linNews2.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }

        }

        //        third Breaking text change
        displayBinding.linBreaking3.setOnClickListener {
            var text = displayBinding.txtBreaking3.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linBreaking3.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtBreaking3.currentTextColor

            editeDialog(text, backgroundColor, textColor)
            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtBreaking3.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtBreaking3.setTextColor(colorText)
                displayBinding.linBreaking3.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }


        }

        //        third news text change
        displayBinding.txtNews3.setOnClickListener {
            var text = displayBinding.txtNews3.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linNews3.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtNews3.currentTextColor

            editeDialog(text, backgroundColor, textColor)
            dialogEditBinding.btnSubmit.setOnClickListener {


                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtNews3.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtNews3.setTextColor(colorText)
                displayBinding.linNews3.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }

        }

        //  hd  text change
        displayBinding.txtHd.setOnClickListener {
            var text = displayBinding.txtHd.text.toString()


            var textColor = displayBinding.txtHd.currentTextColor

            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.txtHd.background
            if (background is ColorDrawable) backgroundColor = background.color

            editeDialog(text, backgroundColor, textColor)


            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtHd.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtHd.setTextColor(colorText)
                displayBinding.txtHd.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }


        }

        //        first layer text change
        displayBinding.linLayer1.setOnClickListener {
            var text = displayBinding.txtLayRepoterName.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linLayer1.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtLayRepoterName.currentTextColor
            editeDialog(text, backgroundColor, textColor)

            dialogEditBinding.linText1.visibility = View.GONE
            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtLayRepoterName.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtLayRepoterName.setTextColor(colorText)
                displayBinding.linLayer1.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }

        }

        //        second layer text change
        displayBinding.linLayer2.setOnClickListener {
            var text = displayBinding.txtLay2.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linLayer2.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtLay2.currentTextColor
            editeDialog(text, backgroundColor, textColor)


            dialogEditBinding.btnSubmit.setOnClickListener {
                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtLay2.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtLay2.setTextColor(colorText)
                displayBinding.linLayer2.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }

        }

        //        third layer text change
        displayBinding.linLayer3.setOnClickListener {
            var text = displayBinding.txtLay3.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linLayer3.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtLay3.currentTextColor
            editeDialog(text, backgroundColor, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtLay3.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtLay3.setTextColor(colorText)
                displayBinding.linLayer3.setBackgroundColor(color)
                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }

        }


        //        Audio Edit
        displayBinding.cdAudioPath.setOnClickListener {
            if (mediaPlayer != null) {
                var audioFileName = displayBinding.txtAudioPath.text.toString()

                var audioDuration = mediaPlayer!!.duration.toLong()

                //audio  duration convert Long value to String Value
                val audioTime = audioDurationCovert(audioDuration)

                //set min & max seek value for audio trimming
                minSeekValue = 0F
                maxSeekValue = audioDuration.toFloat()

                audioEditDialog(audioFileName, audioTime)
            } else {
                Toast.makeText(context, "No Audio", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun editeDialog(text: String, backgroundColor: Int, textColor: Int) {

        editeDialog = Dialog(requireContext())
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(text)
        dialogEditBinding.viewColor.setBackgroundColor(textColor)
        dialogEditBinding.viewColor2.setBackgroundColor(backgroundColor)

        dialogEditBinding.imgChange1.setOnClickListener {

            val colorPickerDialogue = AmbilWarnaDialog(context, mDefaultColor,
                object : OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog) {

                    }

                    override fun onOk(dialog: AmbilWarnaDialog, color: Int) {

                        mDefaultColor = color


                        dialogEditBinding.viewColor.setBackgroundColor(mDefaultColor)
                    }
                })
            colorPickerDialogue.show()
        }

        dialogEditBinding.imgChange2.setOnClickListener {

            val colorPickerDialogue = AmbilWarnaDialog(context, mDefaultColor,
                object : OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog) {

                    }

                    override fun onOk(dialog: AmbilWarnaDialog, color: Int) {

                        mDefaultColor = color


                        dialogEditBinding.viewColor2.setBackgroundColor(mDefaultColor)
                    }
                })
            colorPickerDialogue.show()
        }



        editeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        editeDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editeDialog.show()
    }


    private fun initView() {


        displayBinding.linCaptureVideo.setOnClickListener {
            val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(i, 1)
        }
        displayBinding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        //        Voice Record
        displayBinding.linRecoding.setOnClickListener {
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
                recodingDialogFun()
//            var i = Intent(context, VoiceRecordingActivity::class.java)
//            requireActivity().startActivity(i)
            }
        }
        // creating object of
        // media controller class
        var mediaController = MediaController(context)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(displayBinding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(displayBinding.vidView)
        //volume set
        displayBinding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        displayBinding.vidView.setMediaController(mediaController);

        displayBinding.vidView.scaleX = 1.4f
        displayBinding.vidView.scaleY = 1.4f


//        displayBinding.btnPlay.setOnClickListener {
//            if (displayBinding.vidView.isPlaying) {
//                displayBinding.vidView.pause()
//                displayBinding.btnPlay.text = "Pause"
//                Toast.makeText(context, "Video Pause", Toast.LENGTH_SHORT).show()
//            } else {
//                displayBinding.vidView.start()
//                displayBinding.btnPlay.text = "Play"
//                Toast.makeText(context, "Video Play", Toast.LENGTH_SHORT).show()
//            }
//        }

        // Set up the SeekBar listener to control zoom
        displayBinding.zoomSeekBar.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val scale = 1.0f + (progress / 100.0f) // Adjust the scaling factor
                displayBinding.vidView.scaleX = scale
                displayBinding.vidView.scaleY = scale
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        //Video Export
        displayBinding.cdExploreBtn.setOnClickListener {


            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {

                var centerTextScroll = displayBinding.txtLay2.text.toString()
                var bottomTextScroll = displayBinding.txtLay3.text.toString()
                // Get the text size of the TextView
                val centerTextSize = displayBinding.txtLay2.textSize.toInt()
                val bottomTextSize = displayBinding.txtLay3.textSize.toInt()

                var centerColorText = displayBinding.txtLay2.currentTextColor
                val centerTextColor = String.format("#%06X", 0xFFFFFF and centerColorText)


                var bottomColorText = displayBinding.txtLay3.currentTextColor
                val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)

                val fontPath =
                    getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath

                val fragment = VideoExport1Fragment()
                val bundle = Bundle()

                bundle.putString("videoPath", videoPath)
                bundle.putString("convertImagePath", convertImagePath)
                bundle.putString("centerTextScrollPath", centerTextScroll)
                bundle.putInt("centerTextSize", centerTextSize)
                bundle.putString("centerTextColor", centerTextColor)
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

    private fun recodingDialogFun() {

        recodingDialog = Dialog(requireActivity())
        recodingBinding = DialogRecordingBinding.inflate(layoutInflater)
        recodingDialog.setContentView(recodingBinding.root)

        recodingBinding.imgRecordPlayButton.setOnClickListener {
            if (record == 0) {

                if (!isRecording) {
                    startRecording()

                } else {

                    stopRecording()

                }
                isRecording = !isRecording
            } else if (record == 1) {
                if (!isPlaying) {
                    startPlaying()

                } else {
                    stopPlaying()
                }
//                isPlaying = !isPlaying
            }
        }

        recodingBinding.btnAudioDelete.setOnClickListener {

            mediaPlayer!!.release()
            recodingDialog.dismiss()

        }
        recodingBinding.btnAudioSave.setOnClickListener {
            onSaveAudio()

        }

        recodingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        recodingDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        recodingDialog.show()
        recodingDialog.setCancelable(false)
    }


    private fun seekbar() {

    }

    private fun setVolumeControl(mp: MediaPlayer) {

        mp.setVolume(0f, 0f) //Mute

        var mVolumePlaying = true
        displayBinding.imgVolume.setOnClickListener {
            if (mVolumePlaying) {
                Log.d("TAG", "setVolume ON")
                displayBinding.imgVolume.setImageResource(R.drawable.ic_volume_up)
                mp.setVolume(1f, 1f)//UnMute

            } else {

                Log.d("TAG", "setVolume OFF")
                displayBinding.imgVolume.setImageResource(R.drawable.ic_volume_off)
                mp.setVolume(0f, 0f) //Mute


            }
            mVolumePlaying = !mVolumePlaying

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    displayBinding.vidView.setVideoURI(selectedVideoUri)
                    displayBinding.vidView.requestFocus()


                    // Start playing the video
                    displayBinding.vidView.visibility = View.VISIBLE
//                    displayBinding.linVideoZoom.visibility = View.VISIBLE
//                    displayBinding.linBtn.visibility = View.VISIBLE
                    displayBinding.imgView.visibility = View.INVISIBLE
                    displayBinding.vidView.start()

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
                var selectedImageUri = getImagePathFromURI(uri!!)
                displayBinding.imgNewsLoge.setImageURI(uri)
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

    private fun getImagePathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            requireContext().contentResolver.query(contentUri, projection, null, null, null)


        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            imagePath = it.getString(columnIndex)
            Log.e("TAG", "getPath:${imagePath} ")
            return imagePath
        }

        // If the cursor is null, the query failed
        return contentUri.path // Fallback to using the URI's path
    }


    private fun startRecording() {

        recodingBinding.txtRecordPlayTitle.text = "Stop Recording"
        recodingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_stop_recoding)

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

        recodingBinding.txtRecodingStatus.text = "Recording Started"
        recordingStartTime = System.currentTimeMillis()

        handler.post(updateDuration)
    }

    private fun stopRecording() {
        record = 1

        recodingBinding.txtRecordPlayTitle.text = "Play Audio"
        recodingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_play_recoding)

        recorder?.stop()

        recodingBinding.txtRecodingStatus.text = "Recording Stopped"
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

            recodingBinding.txtDuration.text =
                "Duration: $minutes:${String.format("%02d", remainingSeconds)}"

            handler.postDelayed(this, 1000)
        }
    }


    private fun startPlaying() {

        isPlaying = true

        recodingBinding.txtRecordPlayTitle.text = "Stop Audio"
        recodingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_pause_recoding)

        recodingBinding.txtRecodingStatus.text = "Recording Started Playing"

        recodingBinding.seekBar.visibility = View.VISIBLE

        mediaPlayer = MediaPlayer()
        fileName = "${Environment.getExternalStorageDirectory()}/audio_record.3gp"

        try {
            mediaPlayer?.setDataSource(fileName)
            mediaPlayer?.prepare()
            mediaPlayer?.start()

            updateSeekBar()
            recodingBinding.txtRecordPlayTitle.text = "Stop Audio"


        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.setOnCompletionListener {
            stopPlaying()


        }


        recodingBinding.seekBar.max = mediaPlayer!!.duration


        recodingBinding.seekBar.setOnSeekBarChangeListener(object :
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


        recodingBinding.txtRecordPlayTitle.text = "Play Audio"
        recodingBinding.imgRecordPlayButton.setImageResource(R.drawable.ic_play_recoding)

        recodingBinding.txtRecodingStatus.text = "Recording Play Stopped"


//        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null

        isPlaying = false
        recodingBinding.linSaveAndCancel.visibility = View.VISIBLE
    }


    private fun updateSeekBar() {
        if (mediaPlayer != null) { // Check if mediaPlayer is not null
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) { // Check mediaPlayer is still not null
                        recodingBinding.seekBar.progress = mediaPlayer!!.currentPosition
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
        recodingBinding.txtDuration.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun onSaveAudio() {
        val dialog = Dialog(requireActivity())
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
                val outputFile = File(requireActivity().externalCacheDir, outputFileName)

                if (outputFile.exists()) {
                    Toast.makeText(
                        context,
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

                            audioFilePath = outputFile.absolutePath
                            var audioFileName = audioName
                            // The code related to Intents can be used here if needed.

//                            val fragment = VideoFrame1Fragment()
////                            val bundle = Bundle()
////                            bundle.putString("audioFilePath", outputFile.absolutePath)
////                            bundle.putString("audioFileName", audioName)
////
////                            fragment.arguments = bundle
//                            val transaction = supportFragmentManager.beginTransaction()
//                            transaction.replace(R.id.container, fragment)
//                            transaction.addToBackStack(null)
//                            transaction.commit()

                            setAudio(audioFilePath!!, audioFileName)

                            Toast.makeText(
                                context,
                                "Audio saved as: $outputFileName",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                            recodingDialog.dismiss()


                        } else {
                            Toast.makeText(
                                context,
                                "Source audio file not found",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(context, "Error saving audio", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(context, "Please enter a valid file name", Toast.LENGTH_SHORT).show()
            }
        }

        btnaudiocancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.setCancelable(false)
    }

    private fun setAudio(audioFilePath: String, audioFileName: String) {

        //  audio
//        val audioFilePath = intent.getStringExtra("audioFilePath")
//        val audioFileName = intent.getStringExtra("audioFileName")

        if (audioFilePath != null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(audioFilePath)
                prepare()
            }
            Log.e("TAG", "audiofile: $audioFilePath")


            displayBinding.linAudioPath.visibility = View.VISIBLE



            if (audioFileName != null) {
                displayBinding.txtAudioPath.text = "File Name: $audioFileName.mp3"
            }

            // Audio Play icon Auto Change After Finis Audio
//            if (isPlaying) {
//                displayBinding.imgAudioPlayButton.setImageResource(R.drawable.ic_pause_recoding)
//            } else {
//                displayBinding.imgAudioPlayButton.setImageResource(R.drawable.ic_play_recoding)
//            }


            displayBinding.imgAudioPlayButton.setOnClickListener {
                if (mediaPlayer != null) {
                    if (isPlaying) {
                        mediaPlayer?.pause()
                        displayBinding.imgAudioPlayButton.setImageResource(R.drawable.ic_play_recoding)

                    } else {
                        mediaPlayer?.start()
                        displayBinding.imgAudioPlayButton.setImageResource(R.drawable.ic_pause_recoding)

                        mediaPlayer?.setOnCompletionListener {
                            displayBinding.imgAudioPlayButton.setImageResource(R.drawable.ic_play_recoding)

                        }

                    }
                } else {
                    Toast.makeText(context, "No Audio", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            displayBinding.linAudioPath.visibility = View.GONE // Hide the button
        }


    }


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
            displayBinding.frameView.getWidth(),
            displayBinding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        displayBinding.frameView.draw(canvas)

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


    private fun audioEditDialog(audioFileName: String, audioTime: String) {
        var dialog = Dialog(requireContext())
        var dialogAudioEditBinding = DialogAudioEditBinding.inflate(layoutInflater)
        dialog.setContentView(dialogAudioEditBinding.root)

        dialogAudioEditBinding.txtAudioName.text = audioFileName
        dialogAudioEditBinding.txtEndTime.text = audioTime

        //set Audio duration on seekbar range
        dialogAudioEditBinding.seekBarAudioTrim.maxValue = mediaPlayer!!.duration.toFloat()

        dialogAudioEditBinding.seekBarAudioTrim.setOnSeekBarRangedChangeListener(object :
            SeekBarRangedView.OnSeekBarRangedChangeListener {
            override fun onChanged(view: SeekBarRangedView?, minValue: Float, maxValue: Float) {
                mediaPlayer?.seekTo(minValue.toInt())
            }

            override fun onChanging(view: SeekBarRangedView?, minValue: Float, maxValue: Float) {
                minSeekValue = minValue
                maxSeekValue = maxValue
                dialogAudioEditBinding.txtStartTime.text = audioDurationCovert(minValue.toLong())
                dialogAudioEditBinding.txtEndTime.text = audioDurationCovert(maxValue.toLong())
            }
        })


        dialogAudioEditBinding.btnCansel.setOnClickListener {
            dialog.dismiss()
        }
        dialogAudioEditBinding.btnSave.setOnClickListener {

            var startTime = dialogAudioEditBinding.txtStartTime.text.toString()
            var endTime = dialogAudioEditBinding.txtEndTime.text.toString()

            audioTrimFun(startTime, endTime)
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    private fun audioTrimFun(startTime: String, endTime: String) {
        val trimDuration = maxSeekValue - minSeekValue
//        Log.v("tagName", "seekToValue: $seekToValue")
        Log.v("tagName", "maxValue: $maxSeekValue")
        Log.v("tagName", "minValue: $minSeekValue")
        Log.v("tagName", "trimDuration: $trimDuration")
//        val convertedSeekValue = OptiCommonMethods.convertDuration(seekToValue)
//        Log.v("tagName", "convertedSeekValue: $convertedSeekValue")
        /*val trimDurationLong = OptiCommonMethods.convertDurationInSec(trimDuration.roundToLong())
        Log.v(tagName, "trimDurationLong: $trimDurationLong")*/

//        if (trimDuration.roundToLong() >= seekToValue) {
//            Toast.makeText(activity, "Please trim audio under $convertedSeekValue.", Toast.LENGTH_SHORT).show()
//        } else {
        //output file is generated and send to video processing
        val outputFile = createAudioFile(requireContext())
        Log.v("tagName", "outputFile: ${outputFile.absolutePath}")

//            nextAction = 1

//        OptiVideoEditor.with(context!!)
//            .setType(OptiConstant.AUDIO_TRIM)
//            .setAudioFile(masterAudioFile!!)
//            .setOutputPath(outputFile.absolutePath)
//            .setStartTime(actvStartTime?.text.toString())
//            .setEndTime(actvEndTime?.text.toString())
//            .setCallback(this)
//            .main()

        val query = ffmpegQueryExtension.audioTrimFun(
            audioFilePath!!,
            outputFile.absolutePath,
            startTime,
            endTime
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {
//                exportBinding.txtOutputPath.visibility = View.VISIBLE
//                exportBinding.txtOutputPath.text = logMessage.text
            }

            override fun success() {
//                exportBinding.txtOutputPath.visibility = View.VISIBLE
//                exportBinding.txtOutputPath.text =
//                    String.format(getString(R.string.output_path), outputPath)
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

    fun createAudioFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
            Date()
        )
        val imageFileName: String = "EasyNews" + timeStamp + "_"
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        if (!storageDir!!.exists()) storageDir.mkdirs()
        return File.createTempFile(imageFileName, "mp3", storageDir)
    }

    private fun audioDurationCovert(audioDuration: Long): String {

        return String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(audioDuration),
            TimeUnit.MILLISECONDS.toMinutes(audioDuration) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(audioDuration)), // The change is in this line
            TimeUnit.MILLISECONDS.toSeconds(audioDuration) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(audioDuration))
        )
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

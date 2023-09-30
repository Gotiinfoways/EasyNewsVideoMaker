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
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogRecordingBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentDisplayBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class DisplayFragment : Fragment() {


    lateinit var displayBinding: FragmentDisplayBinding

    private var selectedVideoUri: Uri? = null
    lateinit var storageReference: StorageReference

    lateinit var progressDialog: Dialog

    private val PICK_VIDEO_REQUEST = 1

    // this is the default color of the preview box
    private var mDefaultColor = 0


    // creating a variable for media recorder object class.
    private var mRecorder: MediaRecorder? = null

    // creating a variable for mediaplayer class
    private var mPlayer: MediaPlayer? = null


    // string variable is created for storing a file name
    private var mFileName: String? = null

var isVideoSelected=false
    var videoPath: String? = null
    private var imagePath: String? = null
    lateinit var recodingBinding: DialogRecordingBinding
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding

    private var isRecording = false
    private var isPlaying = false

    private var startTime = 0.0
    private var finalTime = 0.0
    var oneTimeOnly = 0


    private val myHandler = Handler();

    lateinit var ffmpegQueryExtension: FFmpegQueryExtension
    var height: Int? = 0
    var width: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        displayBinding = FragmentDisplayBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        storageReference = FirebaseStorage.getInstance().reference

        ffmpegQueryExtension = FFmpegQueryExtension()

        progressDialog()
        initView()
        frameEdit()
        return displayBinding.root


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

    private fun frameEdit() {
        //text scroll Horizontally
        displayBinding.txtLay1.isSelected = true
        displayBinding.txtLay2.isSelected = true
        displayBinding.txtLay3.isSelected = true

        //set image
        displayBinding.imgNewsLoge.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery_Launcher.launch(intent)
        }
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

//                addImageOnVideo()
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

//                addImageOnVideoBottom()
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
            var text = displayBinding.txtLay1.text.toString()


            var backgroundColor = Color.TRANSPARENT
            val background = displayBinding.linLayer1.background
            if (background is ColorDrawable) backgroundColor = background.color

            var textColor = displayBinding.txtLay1.currentTextColor
            editeDialog(text, backgroundColor, textColor)


            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                var color = Color.TRANSPARENT
                val background = dialogEditBinding.viewColor2.background
                if (background is ColorDrawable) color = background.color

                displayBinding.txtLay1.text = dialogEditBinding.edtText.text.toString()
                displayBinding.txtLay1.setTextColor(colorText)
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



        displayBinding.btnPlay.setOnClickListener {
            if (displayBinding.vidView.isPlaying) {
                displayBinding.vidView.pause()
                displayBinding.btnPlay.text = "Pause"
                Toast.makeText(context, "Video Pause", Toast.LENGTH_SHORT).show()
            } else {
                displayBinding.vidView.start()
                displayBinding.btnPlay.text = "Play"
                Toast.makeText(context, "Video Play", Toast.LENGTH_SHORT).show()
            }
        }

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

        displayBinding.linRecoding.setOnClickListener {
            val dialog = Dialog(requireContext())
            recodingBinding = DialogRecordingBinding.inflate(layoutInflater)
            dialog.setContentView(recodingBinding.root)

            recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey));
            recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey));
            recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))

            // Set up the SeekBar listener to control zoom
            recodingBinding.seekbar
                .setOnSeekBarChangeListener(object :
                    OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar,
                        progress: Int,
                        fromUser: Boolean
                    ) {

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
                })

            recodingBinding.btnRecord.setOnClickListener(View.OnClickListener { // start recording method will
                // start the recording of audio.
                startRecording()
            })
            recodingBinding.btnStop.setOnClickListener { // pause Recording method will
                // pause the recording of audio.
                pauseRecording()
            }
            recodingBinding.btnPlay.setOnClickListener { // play audio method will play
                // the audio which we have recorded
                playAudio()
//                finalTime = mPlayer!!.getDuration().toDouble()
////                startTime = mPlayer!!.getCurrentPosition().toDouble()
//
//                if (oneTimeOnly === 0) {
//                    recodingBinding.seekbar.setMax(finalTime as Int)
//                    oneTimeOnly = 1
//                }
//
//                recodingBinding.durationTextView1.setText(
//                    String.format(
//                        "%d min, %d sec",
//                        TimeUnit.MILLISECONDS.toMinutes(finalTime as Long),
//                        TimeUnit.MILLISECONDS.toSeconds(finalTime as Long) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime as Long))
//                    )
//                )
//
//                recodingBinding.durationTextView2.setText(
//                    String.format(
//                        "%d min, %d sec",
//                        TimeUnit.MILLISECONDS.toMinutes(startTime as Long),
//                        TimeUnit.MILLISECONDS.toSeconds(startTime as Long) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime as Long))
//                    )
//                )
//
//                recodingBinding.seekbar.setProgress(startTime as Int)
//                myHandler.postDelayed(UpdateSongTime, 100)
            }
            recodingBinding.btnStopPlay.setOnClickListener { // pause play method will
                // pause the play of audio
                pausePlaying()
            }

            seekbar()

            recodingBinding.btnSubmit.setOnClickListener {


                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        //Video Export
        displayBinding.cdExploreBtn.setOnClickListener {

//            addTextProcess()
            saveFrameLayoutAsImage()
            val dialog = Dialog(requireContext())
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                progressDialog.show()

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
//            val fragment = VideoExportFragment()
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.container, fragment)
//            transaction.addToBackStack(null)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            transaction.commit()


        }


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
                selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    displayBinding.vidView.setVideoURI(selectedVideoUri)
                    displayBinding.vidView.requestFocus()


                    // Start playing the video
                    displayBinding.vidView.visibility = View.VISIBLE
                    displayBinding.linVideoZoom.visibility = View.VISIBLE
                    displayBinding.linBtn.visibility = View.VISIBLE
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
        // check permission method is used to check
        // that the user has granted permission
        // to record and store the audio.


        // setbackgroundcolor method will change
        // the background color of text view.
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))

        // we are here initializing our filename variable
        // with the path of the recorded audio file.
        mFileName = Environment.getExternalStorageDirectory().absolutePath
        mFileName += "/AudioRecording.3gp"

        // below method is used to initialize
        // the media recorder class
        mRecorder = MediaRecorder()

        // below method is used to set the audio
        // source which we are using a mic.
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)

        // below method is used to set
        // the output format of the audio.
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        // below method is used to set the
        // audio encoder for our recorded audio.
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        // below method is used to set the
        // output file location for our recorded audio
        mRecorder!!.setOutputFile(mFileName)
        try {
            // below method will prepare
            // our audio recorder class
            mRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
        // start method will start
        // the audio recording.
        mRecorder!!.start()
        recodingBinding.idTVstatus.text = "Recording Started"
    }

    fun playAudio() {
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.purple_200))

        // for playing our recorded audio
        // we are using media player class.
        mPlayer = MediaPlayer()
        try {
            // below method is used to set the
            // data source which will be our file name
            mPlayer!!.setDataSource(mFileName)

            // below method will prepare our media player
            mPlayer!!.prepare()

            // below method will start our media player.
            mPlayer!!.start()
            recodingBinding.idTVstatus.text = "Recording Started Playing"
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
    }

    fun pauseRecording() {
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.purple_200))

        // below method will stop
        // the audio recording.
        mRecorder!!.stop()

        // below method will release
        // the media recorder class.
        mRecorder!!.release()
        mRecorder = null
        recodingBinding.idTVstatus.text = "Recording Stopped"

    }

    fun pausePlaying() {
        // this method will release the media player
        // class and pause the playing of our recorded audio.
        mPlayer!!.release()
        mPlayer = null
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.idTVstatus.text = "Recording Play Stopped"

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

    var imagePathTxtBraking1 = ""
    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        val transparentBitmap = Bitmap.createBitmap(
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
        imagePathTxtBraking1 = file.absolutePath
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
    }

    var outputPathBrakingNews1: String = ""
    private fun addImageOnVideo(fileName: String) {


        outputPathBrakingNews1 =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvInputPathImage = imagePathTxtBraking1!!


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

//   var outputPathCityName:String=""
//    private fun addTextProcess() {
//// Generate a unique file name
//        val fileName = "output_" + SimpleDateFormat(
//            "yyyyMMdd_HHmmss",
//            Locale.getDefault()
//        ).format(Date()) + ".mp4"
//        outputPathCityName = Environment.getExternalStorageDirectory().path + "/Download/$fileName"
//
////        var tvInputPathVideo = outputPathBrakingNewsBottom
//        var edtText = displayBinding.txtCityName.text.toString()
//
//// Get the location of the TextView on the screen
//
//        var onScreenX = displayBinding.cdCityName.left.toFloat()
//        var onScreenY = displayBinding.cdCityName.top.toFloat()
//// Output the results
//        Log.e("cardViewPosition", "X: $onScreenX, Y: $onScreenY")
//
//
//        // Get the text size of the TextView
//        val textSize = displayBinding.txtCityName.textSize.toInt()
//
//// Output the result
//        Log.e("TextViewTextSize", "Text Size: $textSize pixels")
//
//
//        var colorText = displayBinding.txtCityName.currentTextColor
//        val textColor = String.format("#%06X", 0xFFFFFF and colorText)
//
//        // Get the background of the CardView
//        val colorInt = displayBinding.cdCityName.cardBackgroundColor
//        // Get the color int from the ColorStateList
//        val cardBackgroundColor = colorInt.defaultColor
//
//        val backgroundColor = String.format("#%06X", 0xFFFFFF and cardBackgroundColor)
////        val cornerRadius: Float = displayBinding.cdCityName.getRadius()
//
//        val fontPath = getFileFromAssets(requireContext(), "Cardo-Bold.ttf").absolutePath
//        val query = ffmpegQueryExtension.addTextOnVideo(
//            tvInputPathVideo,
//            edtText,
//            onScreenX,
//            onScreenY,
//            fontPath = fontPath,
//            isTextBackgroundDisplay = backgroundColor,
//            fontSize = textSize,
//            fontcolor = textColor,
//            output = outputPathCityName
//        )
//        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
//            override fun process(logMessage: LogMessage) {
////                tvOutputPath.text = logMessage.text
//            }
//
//            override fun success() {
////                tvOutputPath.text = String.format(getString(R.string.output_path), outputPath)
////                processStop()
//
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Success", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun cancel() {
////                processStop()
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Cancel", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun failed() {
////                processStop()
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Fail", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//    private fun addWaterMarkProcess(fileName: String) {
//
//
//        var tvInputPathVideo = outputPathCityName
//
//
//        var tvInputPathImage = imagePath!!
//        val outputPath = Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"
//
//        // Get the location of the TextView on the screen
//        val locationOnScreen = IntArray(2)
//        displayBinding.txtBreaking.getLocationOnScreen(locationOnScreen)
//        //Get the x and y coordinates
//
//        var edtXPos = locationOnScreen[0].toFloat()
//        var edtYPos = locationOnScreen[1].toFloat()
//
//        val xPos = width?.let {
//            (edtXPos.toFloat().times(it)).div(100)
//        }
//        val yPos = height?.let {
//            (edtYPos.toFloat().times(it)).div(100)
//        }
//        val query = ffmpegQueryExtension.addVideoWaterMark(
//            tvInputPathVideo,
//            tvInputPathImage,
//            xPos,
//            yPos,
//            outputPath
//        )
//        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
//            override fun process(logMessage: LogMessage) {
////                tvOutputPath.text = logMessage.text
//            }
//
//            override fun success() {
////                tvOutputPath.text = String.format(getString(R.string.output_path), outputPath)
////                processStop()
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Success", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun cancel() {
////                processStop()
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Cancel", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun failed() {
////                processStop()
//                progressDialog.dismiss()
//                Toast.makeText(context, "Video Download Fail", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }



    private val UpdateSongTime: Runnable = object : Runnable {
        override fun run() {
            startTime = mPlayer!!.getCurrentPosition().toDouble()
            recodingBinding.durationTextView2.setText(
                String.format(
                    "%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))
                )
            )
            recodingBinding.seekbar.setProgress(startTime.toInt())
            myHandler.postDelayed(this, 100)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRecording) {
            pauseRecording()
        }
        if (isPlaying) {
            pausePlaying()
        }
        mRecorder!!.release()
        mPlayer!!.release()
    }
}






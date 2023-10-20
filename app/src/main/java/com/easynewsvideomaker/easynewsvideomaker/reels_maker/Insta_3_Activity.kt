package com.easynewsvideomaker.easynewsvideomaker.reels_maker

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityInsta3Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.easynewsvideomaker.easynewsvideomaker.reels_maker.reels_export.ReelsExport1Activity
import com.easynewsvideomaker.easynewsvideomaker.reels_maker.reels_export.ReelsExport3Activity
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class Insta_3_Activity : AppCompatActivity() {
    lateinit var binding: ActivityInsta3Binding
    private val STORAGE_PERMISSION_CODE = 101
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding
    private var mDefaultColor = 0
    var videoPath = ""
    private val PICK_VIDEO_REQUEST = 1
    private var selectedVideoUri: Uri? = null
    lateinit var progressDialog: Dialog
    var convertImagePath = ""
    lateinit var ffmpegQueryExtension: FFmpegQueryExtension
    lateinit var transparentBitmap: Bitmap
    private var userInputText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsta3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ffmpegQueryExtension = FFmpegQueryExtension()
        progressDialog()
        frameEdit()
        initView()
    }

    private fun frameEdit() {
        binding.txtAdditionalText.setOnClickListener {
            var text = binding.txtAdditionalText.text.toString()
            var textColor = binding.txtAdditionalText.currentTextColor
            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

//                userInputText = dialogEditBinding.edtText.text.toString()
//                setText(userInputText, binding.txtAdditionalText)

                binding.txtAdditionalText.text = dialogEditBinding.edtText.text.toString()
                binding.txtAdditionalText.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtheadingTextReels3.setOnClickListener {
            var text = binding.txtheadingTextReels3.text.toString()
            var textColor = binding.txtheadingTextReels3.currentTextColor
            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

//                userInputText = dialogEditBinding.edtText.text.toString()
//                setText(userInputText, binding.txtheadingTextReels3)

                binding.txtheadingTextReels3.text = dialogEditBinding.edtText.text.toString()
                binding.txtheadingTextReels3.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtCityName.setOnClickListener {
            var text = binding.txtCityName.text.toString()
            var textColor = binding.txtCityName.currentTextColor
            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtCityName.text = dialogEditBinding.edtText.text.toString()
                binding.txtCityName.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtBreakingNews.setOnClickListener {
            var text = binding.txtBreakingNews.text.toString()
            var textColor = binding.txtBreakingNews.currentTextColor
            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtBreakingNews.text = dialogEditBinding.edtText.text.toString()
                binding.txtBreakingNews.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }
    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressDialog.setContentView(progressBarBinding.root)

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initView() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        binding.frameView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(this)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(binding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(binding.vidView)
        //volume set
//        binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        binding.vidView.setMediaController(mediaController);

        binding.vidView.scaleX = 1.05f
        binding.vidView.scaleY = 1.05f

        binding.cdExportBtn.setOnClickListener {
            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(this, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
                saveFrameLayoutAsImage()

                var i = Intent(this, ReelsExport3Activity::class.java)
                i.putExtra("videoPath", videoPath)
                i.putExtra("convertImagePath", convertImagePath)
                startActivity(i)
                finish()

            }
        }


//        binding.loutLogo3.setOnClickListener {
//            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            Gallery_Launcher.launch(Gallery)
//        }


    }

//    private fun setText(userInputText: String, textview: TextView) {
//        val i = IntArray(1)
//        i[0] = 0
//        val length = userInputText.length  // Use the length property of the String
//
//        textview.setText("")
//        val handler = object : Handler() {
//            override fun handleMessage(msg: Message) {
//                super.handleMessage(msg)
//                if (i[0] < length) {
//                    val c = userInputText[i[0]]
//                    textview.append(c.toString())
//                    i[0]++
//                }
//            }
//        }
//
//        val timer = Timer()
//        val taskEverySplitSecond = object : TimerTask() {
//            override fun run() {
//                handler.sendEmptyMessage(0)
//                if (i[0] == length) {
//                    timer.cancel()
//                }
//            }
//        }
//        timer.schedule(taskEverySplitSecond, 1, 200)
//    }


    private fun editeDialog(text: String, textColor: Int) {
        editeDialog = Dialog(this)
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(text)
        dialogEditBinding.viewColor.setBackgroundColor(textColor)

        dialogEditBinding.linBackgroundColor.visibility = View.GONE

        dialogEditBinding.imgChange1.setOnClickListener {
            val colorPickerDialogue = AmbilWarnaDialog(this, mDefaultColor,
                object : AmbilWarnaDialog.OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog) {

                    }

                    override fun onOk(dialog: AmbilWarnaDialog, color: Int) {

                        mDefaultColor = color


                        dialogEditBinding.viewColor.setBackgroundColor(mDefaultColor)
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

//    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val data = result.data
//            val uri = data!!.data
//            binding.logo.setImageURI(uri)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    binding.vidView.setVideoURI(selectedVideoUri)
                    binding.vidView.requestFocus()
                    // Start playing the video
                    binding.vidView.visibility = View.VISIBLE
                    binding.vidView.start()

//                    videoPath=selectedVideoUri.path
                    var videoPath = getVideoPathFromURI(selectedVideoUri!!)
                    Log.e("TAG", "onActivityResult:${videoPath} ")
                }
            }
        }

    }


    private fun getVideoPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            this.contentResolver.query(contentUri, projection, null, null, null)

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

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        transparentBitmap = Bitmap.createBitmap(
            binding.frameView.getWidth(),
            binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        binding.frameView.draw(canvas)

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
                Log.d("FFmpeg", logMessage.text)
            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_3_Activity, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_3_Activity, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_3_Activity, "Video Download Fail", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}
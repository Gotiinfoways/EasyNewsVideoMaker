package com.easynewsvideomaker.easynewsvideomaker.reels_maker

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityInsta1Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.easynewsvideomaker.easynewsvideomaker.reels_maker.reels_export.ReelsExport1Activity
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Insta_1_Activity : AppCompatActivity() {
    lateinit var binding: ActivityInsta1Binding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth

    private val STORAGE_PERMISSION_CODE = 101
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding
    private var mDefaultColor = 0
    var videoPath: String? = null
    private val PICK_VIDEO_REQUEST = 1
    private var selectedVideoUri: Uri? = null
    lateinit var progressDialog: Dialog
    var convertImagePath: String? = null
    lateinit var ffmpegQueryExtension: FFmpegQueryExtension
    lateinit var transparentBitmap: Bitmap
    private var userInputText: String = ""
    var scrollTextPath = "Text Scroll is here"
    var gif = " "
    var height: Int? = 0
    var width: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsta1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ffmpegQueryExtension = FFmpegQueryExtension()

        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
        progressDialog()
        frameEdit()
        initView()
    }

    private fun frameEdit() {
        binding.txtAdditionalTextReel1.isSelected = true

// channel logo and repoter name set
        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    var repoterName = postSnapshot.child("repoterName").value
                    var channelLogo = postSnapshot.child("channelLogo").value




                    Glide.with(this@Insta_1_Activity).load(channelLogo).placeholder(R.drawable.news_logo)
                        .into(binding.imgNewsLogo)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.linBraking.setOnClickListener {
            var text = binding.txtBreakingNrwsReel1.text.toString()
            var textColor = binding.txtBreakingNrwsReel1.currentTextColor

            var backgroundColor = Color.TRANSPARENT
            val background = binding.linBraking.background
            if (background is ColorDrawable) backgroundColor = background.color


            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color


//
//                var colorBackground = Color.TRANSPARENT
//                val background = dialogEditBinding.viewColor2.background.setTint(backgroundColor)
//                if (background is ColorDrawable) colorBackground = background.color


                binding.txtBreakingNrwsReel1.text = dialogEditBinding.edtText.text.toString()
                binding.txtBreakingNrwsReel1.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }



        Log.e("TAG", "gif: $gif")

        binding.txtAdditionalTextReel1.setOnClickListener {
            var text = binding.txtAdditionalTextReel1.text.toString()
            var textColor = binding.txtAdditionalTextReel1.currentTextColor
            var backgroundColor = 0
            editeDialog(text, textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

//                userInputText = dialogEditBinding.edtText.text.toString()
//                setText(userInputText, binding.txtAdditionalTextReel1)

                binding.txtAdditionalTextReel1.text = dialogEditBinding.edtText.text.toString()
                binding.txtAdditionalTextReel1.setTextColor(colorText)
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

        var mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vidView)
        mediaController.setMediaPlayer(binding.vidView)
        binding.vidView.setMediaController(mediaController);

        binding.vidView.scaleX = 1.05f
        binding.vidView.scaleY = 1.05f
        binding.cdExport.setOnClickListener {

            if (videoPath == null) {

                Toast.makeText(this, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
                saveFrameLayoutAsImage()
                var textScroll = binding.txtAdditionalTextReel1.text.toString()

                var textColorGet = binding.txtAdditionalTextReel1.currentTextColor
                val textColor = String.format("#%06X", 0xFFFFFF and textColorGet)

                val fontPath = getFileFromAssets(this, "HindVadodara-Bold.ttf").absolutePath

                var i = Intent(this, ReelsExport1Activity::class.java)
                i.putExtra("videoPath", videoPath)
                i.putExtra("convertImagePath", convertImagePath)
                i.putExtra("textScroll", textScroll)
                i.putExtra("textColor", textColor)
                i.putExtra("fontPath", fontPath)
                startActivity(i)
                finish()

            }
        }

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
        dialogEditBinding.imgChange2.setOnClickListener {
            val colorPickerDialogue = AmbilWarnaDialog(this, mDefaultColor,
                object : AmbilWarnaDialog.OnAmbilWarnaListener {
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

    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri = data!!.data
            binding.imgNewsLogo.setImageURI(uri)
        }
    }

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
//        Toast.makeText(this, "image saved", Toast.LENGTH_SHORT).show()
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
    }


}
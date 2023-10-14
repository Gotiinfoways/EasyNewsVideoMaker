package com.easynewsvideomaker.easynewsvideomaker.news_poster

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityPost16Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import yuku.ambilwarna.AmbilWarnaDialog

class Post_16_Activity : AppCompatActivity() {
    lateinit var binding: ActivityPost16Binding
    private val STORAGE_PERMISSION_CODE = 101
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding
    private var mDefaultColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPost16Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        binding.imgpick16.setOnClickListener {
            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallery_Launcher.launch(Gallery)
        }

        binding.imgcapture16.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camera_Launcher.launch(intent)
        }

        binding.cdExploreBtn16.setOnClickListener {
            val z: View = binding.frameView
            z.isDrawingCacheEnabled = true
            val totalHeight: Int = z.height
            val totalWidth: Int = z.width
            z.layout(0, 0, totalWidth, totalHeight)
            z.buildDrawingCache(true)
            val bm: Bitmap = Bitmap.createBitmap(z.getDrawingCache())
            z.isDrawingCacheEnabled = false
            MediaStore.Images.Media.insertImage(contentResolver, bm, null, null)
            Toast.makeText(this, "save image", Toast.LENGTH_SHORT).show()
        }
        binding.txtNewsUpdate.setOnClickListener {
            var text = binding.txtNewsUpdate.text.toString()
            var textColor = binding.txtNewsUpdate.currentTextColor
            editeDialog(text,textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtNewsUpdate.text = dialogEditBinding.edtText.text.toString()
                binding.txtNewsUpdate.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtBreakingNews16.setOnClickListener {
            var text = binding.txtBreakingNews16.text.toString()
            var textColor = binding.txtBreakingNews16.currentTextColor
            editeDialog(text,textColor)


            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtBreakingNews16.text = dialogEditBinding.edtText.text.toString()
                binding.txtBreakingNews16.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtAdditionalText16.setOnClickListener {
            var text = binding.txtAdditionalText16.text.toString()
            var textColor = binding.txtAdditionalText16.currentTextColor
            editeDialog(text,textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtAdditionalText16.text = dialogEditBinding.edtText.text.toString()
                binding.txtAdditionalText16.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtNewsChannel16.setOnClickListener {
            var text = binding.txtNewsChannel16.text.toString()
            var textColor = binding.txtNewsChannel16.currentTextColor
            editeDialog(text,textColor)

            dialogEditBinding.btnSubmit.setOnClickListener {

                var colorText = Color.TRANSPARENT
                val textColor = dialogEditBinding.viewColor.background
                if (textColor is ColorDrawable) colorText = textColor.color

                binding.txtNewsChannel16.text = dialogEditBinding.edtText.text.toString()
                binding.txtNewsChannel16.setTextColor(colorText)
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

    }

    private fun editeDialog(text: String, textColor: Int) {
        editeDialog = Dialog(this)
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(text)
        dialogEditBinding.viewColor.setBackgroundColor(textColor)


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


    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri = data!!.data
            Glide.with(this)
                .load(uri)
                .transform(CenterCrop())
                .into(binding.imgpost16)
        }
    }

    var camera_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult?> {
            override fun onActivityResult(result: ActivityResult?) {
                if (result!!.resultCode == RESULT_OK) {
                    val data = result.data
                    val b = data!!.extras!!["data"] as Bitmap?
                    Glide.with(this@Post_16_Activity)
                        .load(b)
                        .transform(CenterCrop())
                        .into(binding.imgpost16)
                }
            }
        })

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
}
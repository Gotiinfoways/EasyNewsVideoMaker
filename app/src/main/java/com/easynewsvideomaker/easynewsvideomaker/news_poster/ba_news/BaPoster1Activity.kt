package com.easynewsvideomaker.easynewsvideomaker.news_poster.ba_news

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityBaPoster1Binding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogFileSaveBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogImageSetBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ProgressBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BaPoster1Activity : AppCompatActivity() {

    lateinit var binding: ActivityBaPoster1Binding

    lateinit var mDbRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    private val STORAGE_PERMISSION_CODE = 101
    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding
    lateinit var transparentBitmap: Bitmap
    var convertImagePath = ""
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaPoster1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth

        progressDialog()
        frameEdit()
        initView()
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
    private fun frameEdit() {
        // channel logo and repoter name set
        //           user information
        var query: Query =
            mDbRef.child("user").orderByChild("email").equalTo(auth.currentUser?.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {


                    var repoterName = postSnapshot.child("repoterName").value
                    var channelLogo = postSnapshot.child("channelLogo").value



                    Glide.with(this@BaPoster1Activity).load(channelLogo).placeholder(R.drawable.news_logo)
                        .into(binding.imgNewsLoge)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.txtGaliMe.setOnClickListener {
            var text = binding.txtGaliMe.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtGaliMe.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }


        binding.txtAtik.setOnClickListener {
            var text = binding.txtAtik.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtAtik.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txt2004.setOnClickListener {
            var text = binding.txt2004.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txt2004.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txt2022.setOnClickListener {
            var text = binding.txt2022.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txt2022.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtBottom2004.setOnClickListener {
            var text = binding.txtBottom2004.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtBottom2004.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }

        binding.txtBottom2022.setOnClickListener {
            var text = binding.txtBottom2022.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtBottom2022.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }
        binding.txtLorem1.setOnClickListener {
            var text = binding.txtLorem1.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtLorem1.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }
        binding.txtLorem2.setOnClickListener {
            var text = binding.txtLorem2.text.toString()
            editeDialog(text)

            dialogEditBinding.btnSubmit.setOnClickListener {

                binding.txtLorem2.text = dialogEditBinding.edtText.text.toString()
                Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()

            }
        }
    }



    private fun editeDialog(text: String) {
        editeDialog = Dialog(this)
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(text)

        dialogEditBinding.linTextColor.visibility = View.GONE
        dialogEditBinding.linBackgroundColor.visibility = View.GONE
        Log.e("TAG", "editeDialogtext: $text")


        editeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        editeDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editeDialog.show()
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

        binding.imgpick1.setOnClickListener {
            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallery_Launcher.launch(Gallery)
        }

        binding.imgcapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camera_Launcher.launch(intent)
        }




        binding.cdExploreBtn.setOnClickListener {
//            val z: View = binding.linFrame
//            z.isDrawingCacheEnabled = true
//            val totalHeight: Int = z.height
//            val totalWidth: Int = z.width
//            z.layout(0, 0, totalWidth, totalHeight)
//            z.buildDrawingCache(true)
//            val bm: Bitmap = Bitmap.createBitmap(z.getDrawingCache())
//            z.isDrawingCacheEnabled = false
//            MediaStore.Images.Media.insertImage(contentResolver, bm, null, null)
//            Toast.makeText(this, "save image", Toast.LENGTH_SHORT).show()

            val dialog = Dialog(this)
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                if (fileName.isNotEmpty()) {
                    val directoryPath =
                        Environment.getExternalStorageDirectory().path + "/Easy News Maker/News Poster/"
                    val fileNameToCheck = "$fileName.png"

                    if (isFileAlreadyExists(directoryPath, fileNameToCheck)) {
                        // The file already exists
                        // You can handle this case, e.g., show an error message
                        Toast.makeText(
                            this,
                            "File with the same name already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // The file does not exist, you can proceed with your operations
//                        downloadProgressDialog.show()

                        saveFrameLayoutAsImage(fileName)
                        dialog.dismiss()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please enter a valid file name",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }



    }



    fun isFileAlreadyExists(directoryPath: String, fileName: String): Boolean {
        val directory = File(directoryPath)
        if (directory.exists() && directory.isDirectory) {
            val file = File(directory, fileName)
            return file.exists() // Check if the file exists in the specified directory
        }
        return false // The directory does not exist or is not a directory
    }
    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri = data!!.data
//            binding.image1.setImageURI(uri)
            imageSetFromGalleryDialog(uri)
        }
    }

    var camera_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult?> {
            override fun onActivityResult(result: ActivityResult?) {
                if (result!!.resultCode == RESULT_OK) {
                    val data = result.data
                    val b = data!!.extras!!["data"] as Bitmap?
//                    binding.image1.setImageBitmap(b)
                    imageSetFromCameraDialog(b)
                }
            }
        })

    private fun imageSetFromGalleryDialog(uri: Uri?) {
        var dialog = Dialog(this)
        var dialogBinding = DialogImageSetBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        var selectedFrame: String? = null

//        #EA5C5C
        val selectedColor = Color.parseColor("#EA5C5C")
        val notSelectedColor = Color.parseColor("#FFB2B2")

        dialogBinding.linFrame1.setOnClickListener {
            selectedFrame = "frame1"

            dialogBinding.linFrame1.setBackgroundColor(selectedColor)
            dialogBinding.linFrame2.setBackgroundColor(notSelectedColor)
        }

        dialogBinding.linFrame2.setOnClickListener {
            selectedFrame = "frame2"

            dialogBinding.linFrame1.setBackgroundColor(notSelectedColor)
            dialogBinding.linFrame2.setBackgroundColor(selectedColor)
        }
        dialogBinding.btnSubmit.setOnClickListener {
            if (selectedFrame == "frame1") {
                binding.image1.setImageURI(uri)
                dialog.dismiss()
            } else if (selectedFrame == "frame2") {
                binding.image2.setImageURI(uri)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Select any frame", Toast.LENGTH_SHORT).show()
            }
        }


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
        dialog.setCancelable(false)
    }
    private fun imageSetFromCameraDialog(bitmap: Bitmap?) {
        var dialog = Dialog(this)
        var dialogBinding = DialogImageSetBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        var selectedFrame: String? = null

//        #EA5C5C
        val selectedColor = Color.parseColor("#EA5C5C")
        val notSelectedColor = Color.parseColor("#FFB2B2")

        dialogBinding.linFrame1.setOnClickListener {
            selectedFrame = "frame1"

            dialogBinding.linFrame1.setBackgroundColor(selectedColor)
            dialogBinding.linFrame2.setBackgroundColor(notSelectedColor)
        }

        dialogBinding.linFrame2.setOnClickListener {
            selectedFrame = "frame2"

            dialogBinding.linFrame1.setBackgroundColor(notSelectedColor)
            dialogBinding.linFrame2.setBackgroundColor(selectedColor)
        }
        dialogBinding.btnSubmit.setOnClickListener {
            if (selectedFrame == "frame1") {
                binding.image1.setImageBitmap(bitmap)
                dialog.dismiss()
            } else if (selectedFrame == "frame2") {
                binding.image2.setImageBitmap(bitmap)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Select any frame", Toast.LENGTH_SHORT).show()
            }
        }


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
        dialog.setCancelable(false)
    }
    fun saveFrameLayoutAsImage(fileName: String) {
        // Create a transparent Bitmap
        transparentBitmap = Bitmap.createBitmap(
            binding.linFrame.getWidth(),
            binding.linFrame.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        binding.linFrame.draw(canvas)

        // Save the Bitmap as an image file
//        val file = File(Environment.getExternalStorageDirectory(), "transparent_image.png")
        val fileOutputPath = File(Environment.getExternalStorageDirectory().path + "/Easy News Maker/News Poster/$fileName.png")
        try {
            val outputStream = FileOutputStream(fileOutputPath)
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        convertImagePath = fileOutputPath.absolutePath
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
        Toast.makeText(this, "save image", Toast.LENGTH_SHORT).show()

        binding.txtOutputPath.visibility = View.VISIBLE
        binding.txtOutputPath.text =
            String.format(getString(R.string.output_path), fileOutputPath)
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
}
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
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.easynewsvideomaker.easynewsvideomaker.databinding.DialogEditBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.FragmentVideoFrame12Binding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class VideoFrame12Fragment : Fragment() {
    lateinit var videoFrame12Binding: FragmentVideoFrame12Binding

    lateinit var editeDialog: Dialog
    lateinit var dialogEditBinding: DialogEditBinding

    var convertImagePath: String? = null
    var videoPath: String? = null
    private val PICK_VIDEO_REQUEST = 1

    // this is the default color of the preview box
    private var mDefaultColor = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        videoFrame12Binding = FragmentVideoFrame12Binding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        initView()
        frameEdit()
        return videoFrame12Binding.root
    }


    private fun frameEdit() {

        //set image
        videoFrame12Binding.imgNewsLoge.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery_Launcher.launch(intent)
        }

        //       कर्नाटक name text change
        videoFrame12Binding.linBox1.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle1.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub1.text.toString()



            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle1.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub1.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       BJP name text change
        videoFrame12Binding.linBox2.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle2.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub2.text.toString()



            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub2.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
        //       CONG name text change
        videoFrame12Binding.linBox3.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle3.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub3.text.toString()



            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle3.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub3.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       JDS name text change
        videoFrame12Binding.linBox4.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle4.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub4.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle4.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub4.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       OTH name text change
        videoFrame12Binding.linBox5.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle5.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub5.text.toString()



            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle5.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub5.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       कर्नाटक name text change
        videoFrame12Binding.txtBoxTitle6.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle6.text.toString()

            var textSub = ""

            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.GONE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle6.text = dialogEditBinding.edtText.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       रीजन name text change
        videoFrame12Binding.txtBoxSubTitle1.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle1.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub6.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle1.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub6.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //       बड़े शहर name text change
        videoFrame12Binding.txtBoxSubTitle2.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle2.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub7.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle2.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub7.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //       उत्तर प्रदेश name text change
        videoFrame12Binding.linBox7.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle8.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub8.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle8.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub8.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //      BJP name text change
        videoFrame12Binding.linBox8.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle3.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub9.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle3.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub9.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //      Sp name text change
        videoFrame12Binding.linBox9.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle4.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub10.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle4.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub10.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //      BSP name text change
        videoFrame12Binding.linBox10.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle5.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub11.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle5.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub11.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //      CONG name text change
        videoFrame12Binding.linBox11.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle6.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub13.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle6.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub13.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //      OTH name text change
        videoFrame12Binding.linBox12.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxSubTitle7.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub14.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxSubTitle7.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub14.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }



        //      युपी मेयर name text change
        videoFrame12Binding.linBox13.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle10.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub15.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle10.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub15.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }


        //     BJP name text change
        videoFrame12Binding.linBox14.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle11.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub16.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle11.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub16.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     CONG name text change
        videoFrame12Binding.linBox15.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle12.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub17.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle12.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub17.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     JDS name text change
        videoFrame12Binding.linBox16.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle13.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub18.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle13.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub18.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }

        //     OTH name text change
        videoFrame12Binding.linBox17.setOnClickListener {
            var textTitle = videoFrame12Binding.txtBoxTitle14.text.toString()
            var textSub = videoFrame12Binding.txtBoxSub19.text.toString()


            editeDialog(textTitle, textSub)

            dialogEditBinding.linText2.visibility = View.VISIBLE

            dialogEditBinding.btnSubmit.setOnClickListener {


                videoFrame12Binding.txtBoxTitle14.text = dialogEditBinding.edtText.text.toString()
                videoFrame12Binding.txtBoxSub19.text = dialogEditBinding.edtText2.text.toString()

                Toast.makeText(context, "Your data is Change", Toast.LENGTH_SHORT).show()
                editeDialog.dismiss()
            }
        }
    }

    private fun editeDialog(textTitle: String, textSub: String) {

        editeDialog = Dialog(requireContext())
        dialogEditBinding = DialogEditBinding.inflate(layoutInflater)
        editeDialog.setContentView(dialogEditBinding.root)

        dialogEditBinding.edtText.setText(textTitle)
        dialogEditBinding.edtText2.setText(textSub)



        dialogEditBinding.linTextColor.visibility = View.GONE
        dialogEditBinding.linBackgroundColor.visibility = View.GONE




        editeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
        editeDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editeDialog.show()
    }

    private fun initView() {

        videoFrame12Binding.linCaptureVideo.setOnClickListener {
            val i = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(i, 1)
        }
        videoFrame12Binding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(context)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoFrame12Binding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoFrame12Binding.vidView)
        //volume set
//        videoFrame2Binding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        videoFrame12Binding.vidView.setMediaController(mediaController);


        //Video Export
        videoFrame12Binding.cdExploreBtn.setOnClickListener {


            saveFrameLayoutAsImage()
            if (videoPath == null) {

                Toast.makeText(context, "Please Select Video", Toast.LENGTH_SHORT).show()

            } else {
//
//                var centerTextScroll = videoFrame12Binding.txtLay1.text.toString()
//                var bottomTextScroll = videoFrame12Binding.txtLay2.text.toString()
//                // Get the text size of the TextView
//                val centerTextSize = videoFrame12Binding.txtLay1.textSize.toInt()
//                val bottomTextSize = videoFrame12Binding.txtLay2.textSize.toInt()
//
//                var centerColorText = videoFrame12Binding.txtLay1.currentTextColor
//                val centerTextColor = String.format("#%06X", 0xFFFFFF and centerColorText)
//
//
//                var bottomColorText = videoFrame12Binding.txtLay2.currentTextColor
//                val bottomTextColor = String.format("#%06X", 0xFFFFFF and bottomColorText)
//
//
//                val fontPath =
//                    getFileFromAssets(requireContext(), "HindVadodara-Bold.ttf").absolutePath
//
//                val fragment = VideoExportFragment()
//                val bundle = Bundle()
//
//                bundle.putString("videoPath", videoPath)
//                bundle.putString("convertImagePath", convertImagePath)
//                bundle.putString("centerTextScrollPath", centerTextScroll)
//                bundle.putInt("centerTextSize", centerTextSize)
//                bundle.putString("centerTextColor", centerTextColor)
//                bundle.putString("bottomTextScrollPath", bottomTextScroll)
//                bundle.putInt("bottomTextSize", bottomTextSize)
//                bundle.putString("bottomTextColor", bottomTextColor)
//                bundle.putString("fontPath", fontPath)
//
//                fragment.arguments = bundle
//                val transaction = requireActivity().supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.container, fragment)
//                transaction.addToBackStack(null)
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                transaction.commit()

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    videoFrame12Binding.vidView.setVideoURI(selectedVideoUri)
                    videoFrame12Binding.vidView.requestFocus()


                    // Start playing the video
                    videoFrame12Binding.vidView.visibility = View.VISIBLE
                    videoFrame12Binding.linVideoBackground.visibility = View.GONE
//                    videoFrame3Binding.linVideoZoom.visibility = View.VISIBLE
//                    videoFrame3Binding.linBtn.visibility = View.VISIBLE
//                    videoFrame12Binding.imgView.visibility = View.INVISIBLE
                    videoFrame12Binding.vidView.start()

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
//                var selectedImageUri = getImagePathFromURI(uri!!)
                videoFrame12Binding.imgNewsLoge.setImageURI(uri)
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

//    private fun getImagePathFromURI(contentUri: Uri): String? {
//        val projection = arrayOf(MediaStore.Video.Media.DATA)
//        val cursor: Cursor? =
//            requireContext().contentResolver.query(contentUri, projection, null, null, null)
//
//
//        cursor?.use {
//            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            it.moveToFirst()
//            imagePath = it.getString(columnIndex)
//            Log.e("TAG", "getPath:${imagePath} ")
//            return imagePath
//        }
//
//        // If the cursor is null, the query failed
//        return contentUri.path // Fallback to using the URI's path
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

    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        var transparentBitmap = Bitmap.createBitmap(
            videoFrame12Binding.frameView.getWidth(),
            videoFrame12Binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        videoFrame12Binding.frameView.draw(canvas)

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


}
package com.easynewsvideomaker.easynewsvideomaker.merge_file

class FFmpegQueryExtension {

    //two text scroll,text scroll speed,image  size set on video and video size  youtube size
    fun addTextOnVideo(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeBottomColor: String,
        textInputeSize: Int,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        val fontPath = "/system/fonts/DroidSans.ttf"


//        var textCenter = "drawtext=fontfile='$fontPath':text='textInputeCenter':fontsize=20:fontcolor=white:x=w-(t-4.5)*100/2:y=h-th-60"
//        var textBottom = "drawtext=fontfile='$fontPath':text='textInputeBottom':fontsize=20:fontcolor=black:x=w-(t-4.5)*100/2:y=h-th-60"
        var textCenter = "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom = "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0, $textCenter, $textBottom")
            add("-c:a")
            add("copy")
            add("-s")
            add("1920x1080")
            add("-movflags")
            add("+faststart")
            add(outputPath)
        }

        return inputs.toArray(arrayOfNulls(inputs.size))
    }
    //frame image set in full screen
//    fun addImageOnVideo(inputVideo: String, imageInput: String,output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[1][0]scale2ref[i][m];[m][i]overlay[v]")
//            add("-map")
//            add("[v]")
//            add("-map")
//            add("0:a?")
//            add("-ac")
//            add("2")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }

    //video size set for  youtube Video size
//    fun addImageOnVideo(inputVideo: String, imageInput: String,output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=(W-w)/2:(H-h)/2")
//            add("-s")
//            add("1920x1080")
//            add( "-c:a")
//            add( "copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }


    //video size set  for youtube video size and image set in full screen
    fun addImageOnVideo(inputVideo: String, imageInput: String, output: String): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1][0]scale2ref[i][m];[m][i]overlay[v]")
            add("-map")
            add("[v]")
            add("-map")
            add("0:a?")
            add("-ac")
            add("2")
            add("-s")
            add("1920x1080")
            add("-c:a")
            add("copy")
            add(output)
        }

        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    //    fun addImageOnVideo(inputVideo: String, imageInput: String,output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-i")
//            add(imageInput)
//            add("-filter_complex")
//            add("[0:v][1:v]overlay=(W-w)/2:0")
//            add("-s")
//            add("1920x1080")
//            add( "-c:a")
//            add( "copy")
//            add(output)
//        }
//
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }
    fun addImageOnVideoBottom(
        inputVideo: String,
        imageInput: String,
        posX: Float?,
        posY: Float?,
        output: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("overlay=(main_w-overlay_w):(main_h-overlay_h)")
            add("-preset")
            add("ultrafast")
            add(output)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    //only text show on video
//    fun addTextOnVideo(inputVideo: String, textInput: String, posX: Float?, posY: Float?, fontPath: String, isTextBackgroundDisplay: String, fontSize: Int, fontcolor: String, output: String): Array<String> {
//        val inputs: ArrayList<String> = ArrayList()
//        var borderQuery = ""
////        if (isTextBackgroundDisplay) {
//        borderQuery = ":box=1:boxcolor=$isTextBackgroundDisplay:boxborderw=0"
////        }
//        inputs.apply {
//            add("-i")
//            add(inputVideo)
//            add("-vf")
//            add("drawtext=text='$textInput':fontfile=$fontPath:x=$posX:y=$posY:fontsize=$fontSize:fontcolor=$fontcolor${borderQuery.trim()}")
//            add("-c:a")
//            add("copy")
//            add("-preset")
//            add("ultrafast")
//            add(output)
//        }
//        return inputs.toArray(arrayOfNulls<String>(inputs.size))
//    }

    fun addVideoWaterMark(
        inputVideo: String,
        imageInput: String,
        posX: Float?,
        posY: Float?,
        output: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1:v]scale=65:65 [watermark]; [0:v][watermark]overlay=(main_w-overlay_w)-10:90")
            add("-preset")
            add("ultrafast")
            add(output)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }
}
package com.easynewsvideomaker.easynewsvideomaker.merge_file

class FFmpegQueryExtension {

    //two text scroll,text scroll speed,image  size set on video and video size  youtube size
    fun addFrame1VideoEditFun(
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
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



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

    //frame 2 & 3
    fun addFrame2VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeBottomColor: String,
        textInputeSize1: Int,
        textInputeSize: Int,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize1:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



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

    //frame 4 & 5
    fun addFrame4_and_5VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeBottomColor: String,
        textInputeSize: Int,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0, $textBottom")
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

    //frame 6 & 7
    fun addFrame6_and_7VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeBottomColor: String,
        textInputeSize: Int,
        textInputeSize2: Int,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize2:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



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

    //frame 8 & 10
    fun addFrame8_and_10VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeTop: String,
        yTopPosition: Int,
        textInputeSize1: Int,
        textInputeTopColor: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeSize2: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeSize3: Int,
        textInputeBottomColor: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textTop =
            "drawtext=fontfile='$fontPath':text='$textInputeTop':fontsize=$textInputeSize1:fontcolor=$textInputeTopColor:x=w-(mod(5*n\\,w+tw)):y=$yTopPosition"
        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize2:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize3:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0,$textTop, $textCenter, $textBottom")
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

    //frame 9
    fun addFrame9VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeTop: String,
        yTopPosition: Int,
        textInputeSize1: Int,
        textInputeTopColor: String,
        textInputeTop2: String,
        yTop2Position: Int,
        textInputeSize2: Int,
        textInputeTop2Color: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeSize3: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeSize4: Int,
        textInputeBottomColor: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textTop =
            "drawtext=fontfile='$fontPath':text='$textInputeTop':fontsize=$textInputeSize1:fontcolor=$textInputeTopColor:x=w-(mod(5*n\\,w+tw)):y=$yTopPosition"
        var textTop2 =
            "drawtext=fontfile='$fontPath':text='$textInputeTop2':fontsize=$textInputeSize2:fontcolor=$textInputeTop2Color:x=w-(mod(5*n\\,w+tw)):y=$yTop2Position"
        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize3:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize4:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0,$textTop,$textTop2, $textCenter, $textBottom")
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

    //frame 11
    fun addFrame11VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        textInputeTop: String,
        yTopPosition: Int,
        textInputeSize1: Int,
        textInputeTopColor: String,
        textInputeCenter: String,
        yCenterPosition: Int,
        textInputeSize2: Int,
        textInputeCenterColor: String,
        textInputeBottom: String,
        yBottomPosition: Int,
        textInputeSize3: Int,
        textInputeBottomColor: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var textTop =
            "drawtext=fontfile='$fontPath':text='$textInputeTop':fontsize=$textInputeSize1:fontcolor=$textInputeTopColor:x=w-(mod(5*n\\,w+tw)):y=$yTopPosition"

        var textCenter =
            "drawtext=fontfile='$fontPath':text='$textInputeCenter':fontsize=$textInputeSize2:fontcolor=$textInputeCenterColor:x=w-(mod(5*n\\,w+tw)):y=$yCenterPosition"
        val textBottom =
            "drawtext=fontfile='$fontPath':text='$textInputeBottom':fontsize=$textInputeSize3:fontcolor=$textInputeBottomColor:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"



        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0,$textTop, $textCenter, $textBottom")
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

    //frame 12  &13

    fun addFrame12VideoEditFun(
        inputVideo: String, imageInput: String, output: String,
        videoWidth: Int,
        videoHeight: Int
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0")
            add("-c:a")
            add("copy")
            add("-s")
            add("1920x1080")
            add(output)
        }

        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }


    //frame 14

    fun addFrame14VideoEditFun(
        tvInputPathVideo: String,
        tvInputPathImage: String,
        outputPath: String,
        videoWidth: Int,
        videoHeight: Int,
        bottomTextScroll: String?,
        yBottomPosition: Int,
        textInputeSize3: Int,
        fontPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()

        val textBottom =
            "drawtext=fontfile='$fontPath':text='$bottomTextScroll':fontsize=$textInputeSize3:fontcolor=black:x=w-(mod(5*n\\,w+tw)):y=$yBottomPosition"

        inputs.apply {
            add("-i")
            add(tvInputPathVideo)
            add("-i")
            add(tvInputPathImage)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0,$textBottom")
            add("-c:a")
            add("copy")
            add("-s")
            add("1920x1080")
            add("-movflags")
            add("+faststart")
            add(outputPath)
        }

        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }


    //this code work only orientation portrait video
    //reels frame 1
    fun addFrame1ReelsMakerFun(
        inputVideo: String,
        imageInput: String,
        textInput: String,
        textInputeColor: String,
        textInputeSize: Int,
        yPosition: Int,
        fontPath: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()

        var textScroll =
            "drawtext=fontfile=$fontPath:text='$textInput':fontsize=$textInputeSize:fontcolor=$textInputeColor:x=w-(mod(5*n\\,w+tw)):y=$yPosition"

        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0,$textScroll")
            add("-c:a")
            add("copy")
            add("-movflags")
            add("+faststart")
            add(outputPath)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }


    //this code work only orientation portrait video
    //reels frame 2
    fun addFrame2ReelsMakerFun(
        inputVideo: String,
        imageInput: String,
        videoWidth: Int,
        videoHeight: Int,
        outputPath: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()



        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-i")
            add(imageInput)
            add("-filter_complex")
            add("[1:v]scale=$videoWidth:$videoHeight[scaled_image];[0:v][scaled_image]overlay=0:0")
            add("-c:a")
            add("copy")
            add("-movflags")
            add("+faststart")
            add(outputPath)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    fun audioTrimFun(
        audioFilePath: String,
        outputFile: String,
        startTime: String,
        endTime: String
    ): Array<String> {
        val inputs: ArrayList<String> = ArrayList()



        inputs.apply {
            add("-y")
            add("-i")
            add(audioFilePath)
            add("-ss")
            add(startTime)
            add("-to")
            add(endTime)
            add("copy")
            add(outputFile)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
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
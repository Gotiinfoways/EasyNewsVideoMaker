package com.easynewsvideomaker.easynewsvideomaker.merge_file

class FFmpegQueryExtension {


    fun addImageOnVideo(inputVideo: String, imageInput: String,output: String): Array<String> {
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
            add(output)
        }

        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    fun addImageOnVideoBottom(inputVideo: String, imageInput: String, posX: Float?, posY: Float?, output: String): Array<String> {
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
    fun addTextOnVideo(inputVideo: String, textInput: String, posX: Float?, posY: Float?, fontPath: String, isTextBackgroundDisplay: String, fontSize: Int, fontcolor: String, output: String): Array<String> {
        val inputs: ArrayList<String> = ArrayList()
        var borderQuery = ""
//        if (isTextBackgroundDisplay) {
        borderQuery = ":box=1:boxcolor=$isTextBackgroundDisplay:boxborderw=0"
//        }
        inputs.apply {
            add("-i")
            add(inputVideo)
            add("-vf")
            add("drawtext=text='$textInput':fontfile=$fontPath:x=$posX:y=$posY:fontsize=$fontSize:fontcolor=$fontcolor${borderQuery.trim()}")
            add("-c:a")
            add("copy")
            add("-preset")
            add("ultrafast")
            add(output)
        }
        return inputs.toArray(arrayOfNulls<String>(inputs.size))
    }

    fun addVideoWaterMark(inputVideo: String, imageInput: String, posX: Float?, posY: Float?, output: String): Array<String> {
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
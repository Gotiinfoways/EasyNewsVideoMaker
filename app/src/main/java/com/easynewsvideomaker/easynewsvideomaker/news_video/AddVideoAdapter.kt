package com.easynewsvideomaker.easynewsvideomaker.news_video


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.AdvtFragment

class AddVideoAdapter(
    var context: Context,var play:(Int)->Unit
) :
    RecyclerView.Adapter<AddVideoAdapter.MyViewHolder>() {

    var videoList = ArrayList<AddVideoModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playButton = itemView.findViewById<ImageView>(R.id.imgVideoPlayButton)
        var videoName = itemView.findViewById<TextView>(R.id.txtVideoPath)
        var edit = itemView.findViewById<CardView>(R.id.cdVideoAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_add_video_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.videoName.text = videoList[position].videoName

        Log.e("TAG", "onBindViewHolder: ${videoList[position].videoName}  ${videoList[position].videoName}", )
        holder.playButton.setOnClickListener {
            play.invoke(position)
            Log.e("TAG", "onBindViewHolder: $position")
        }



    }

    fun updateList(videoList: ArrayList<AddVideoModel>) {
        this.videoList = videoList
    }

}
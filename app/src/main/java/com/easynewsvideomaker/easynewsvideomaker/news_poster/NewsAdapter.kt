package com.easynewsvideomaker.easynewsvideomaker.news_poster

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.easynewsvideomaker.easynewsvideomaker.R

class NewsAdapter(var context: Context, var click: (Int) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    var imageList = ArrayList<Int>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageDisplay = itemView.findViewById<ImageView>(R.id.imgDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageDisplay.setImageResource(imageList[position])

        holder.imageDisplay.setOnClickListener {
            click.invoke(position)
            Log.e("TAG", "onBindViewHolder: $position")
        }
    }

    fun updateList(imageList: ArrayList<Int>) {
        this.imageList = imageList
    }

}
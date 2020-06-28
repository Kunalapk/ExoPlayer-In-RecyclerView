package com.kunal.videoexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(private val customModelList: MutableList<VideoModel>,private val onPlayerCallBack: OnPlayerCallBack): RecyclerView.Adapter<VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideoViewHolder(layoutInflater.inflate(R.layout.item_video,parent,false),onPlayerCallBack)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(customModelList[position])
    }

    override fun getItemCount() = customModelList.size
}
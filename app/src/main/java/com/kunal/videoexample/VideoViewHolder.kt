package com.kunal.videoexample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_video.view.*

class VideoViewHolder(itemView: View,private val onPlayerCallBack: OnPlayerCallBack) : RecyclerView.ViewHolder(itemView) {

    private var videoModel:VideoModel? = null

    internal fun bind(videoModel: VideoModel) {
        this.videoModel = videoModel
    }


    internal fun startVideo(){
        if(videoModel!=null && videoModel!!.media_url!=null){
            onPlayerCallBack.onResumePlayer(itemView.exoPlayerView,videoModel!!)
        }
    }

    internal fun stopVideo(){
        if(videoModel!=null && videoModel!!.media_url!=null){
            onPlayerCallBack.onStopPlayer(itemView.exoPlayerView,videoModel!!)
        }
    }
}
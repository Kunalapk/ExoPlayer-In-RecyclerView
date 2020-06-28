package com.kunal.videoexample

import com.google.android.exoplayer2.ui.PlayerView

interface OnPlayerCallBack {
    fun onResumePlayer(playerView: PlayerView,videoModel: VideoModel)
    fun onStopPlayer(playerView: PlayerView,videoModel: VideoModel)
}
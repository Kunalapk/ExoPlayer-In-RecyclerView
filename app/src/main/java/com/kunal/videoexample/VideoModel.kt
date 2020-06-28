package com.kunal.videoexample

import java.io.Serializable

data class VideoModel(
    var media_url:String? = null,
    var seekTo:Long = 0,
    var view_type:String? = null
):Serializable
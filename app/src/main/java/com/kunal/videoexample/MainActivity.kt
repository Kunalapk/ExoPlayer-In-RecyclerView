package com.kunal.videoexample

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private val videoList:MutableList<VideoModel> = arrayListOf()
    private var currentPlayingPosition:Int = -1

    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePlayer()

        videoList.add(VideoModel("https://instagram.fdel1-1.fna.fbcdn.net/v/t50.2886-16/105881295_261775901581426_4045224881898810158_n.mp4?_nc_ht=instagram.fdel1-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=EiKrL0UsTGsAX_VjNCb&oe=5EF986BA&oh=171a2c6381f72297aacccffa79c0ae7e",0,"vid"))
        videoList.add(VideoModel(" https://instagram.fdel1-1.fna.fbcdn.net/v/t50.2886-16/106208899_150060009982690_127017920153557766_n.mp4?_nc_ht=instagram.fdel1-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=8nJ4uwH7ePAAX_LArts&oe=5EF9914E&oh=9381b165cdebda8f4c789ad7a5a906a7",6,"vid"))
        videoList.add(VideoModel(" https://instagram.fdel1-4.fna.fbcdn.net/v/t50.2886-16/84208047_153192689646098_7017452824780387789_n.mp4?_nc_ht=instagram.fdel1-4.fna.fbcdn.net&_nc_cat=102&_nc_ohc=p-ZOXBKZAd8AX94-nop&oe=5EFA0298&oh=94ff197a252bdedbe82ffa399f301496",0,"vid"))
        videoList.add(VideoModel(" https://instagram.fdel1-1.fna.fbcdn.net/v/t50.2886-16/106252553_717311732420277_2916251729754626245_n.mp4?_nc_ht=instagram.fdel1-1.fna.fbcdn.net&_nc_cat=109&_nc_ohc=apv_58Lh0hYAX_bfLzy&oe=5EF975E4&oh=f3bb043e9dcc7282d34c67605a430903",0,"vid"))
        videoList.add(VideoModel("https://instagram.fdel1-1.fna.fbcdn.net/v/t50.2886-16/105881295_261775901581426_4045224881898810158_n.mp4?_nc_ht=instagram.fdel1-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=EiKrL0UsTGsAX_VjNCb&oe=5EF986BA&oh=171a2c6381f72297aacccffa79c0ae7e",0,"vid"))
        videoList.add(VideoModel("https://instagram.fdel1-1.fna.fbcdn.net/v/t50.2886-16/105881295_261775901581426_4045224881898810158_n.mp4?_nc_ht=instagram.fdel1-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=EiKrL0UsTGsAX_VjNCb&oe=5EF986BA&oh=171a2c6381f72297aacccffa79c0ae7e",0,"vid"))


        rvVideo.apply {
            adapter = VideoAdapter(videoList,onPlayerCallBack)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(recyclerViewListener)
        }
    }

    private fun initializePlayer() {

        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(this)

        player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
    }

    private fun playVideo(playerView: PlayerView,seekTo:Long,source:String){
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent)).setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(Uri.parse(source))
        player.stop()
        playerView.player = player
        player.playWhenReady = true
        player.seekTo(seekTo)
        player.prepare(mediaSource)

    }


    private val onPlayerCallBack:OnPlayerCallBack = object :OnPlayerCallBack{
        override fun onResumePlayer(playerView: PlayerView,videoModel: VideoModel) {
            if(videoModel.media_url!=null){
                playVideo(playerView,videoModel.seekTo,videoModel.media_url!!)
            }
        }

        override fun onStopPlayer(playerView: PlayerView, videoModel: VideoModel) {
            playerView.player = null
        }
    }

    private val recyclerViewListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                /*val first = (rvVideo.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val last = (rvVideo.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val diff = first-last
                var currentPosition = 0
                if(diff==1 || diff==-1){
                    currentPosition = first
                }else if(diff<0){
                    currentPosition = diff*-1
                }else{
                    currentPosition = diff
                }*/

                
                var currentPosition = (rvVideo.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if(currentPosition==-1){
                    currentPosition = (rvVideo.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }

                Log.d(TAG,"CurrentPosition - "+currentPosition)


                if(currentPosition!=-1 && currentPlayingPosition!=currentPosition){

                    if(currentPlayingPosition!=-1 && currentPosition!=-1){
                        val oldVideoViewHolder :RecyclerView.ViewHolder? = rvVideo.findViewHolderForAdapterPosition(currentPlayingPosition)

                        if(oldVideoViewHolder!=null && oldVideoViewHolder is VideoViewHolder){
                            oldVideoViewHolder.stopVideo()
                        }
                    }


                    val videoViewHolder :RecyclerView.ViewHolder? = rvVideo.findViewHolderForAdapterPosition(currentPosition)
                    if(videoViewHolder!=null && videoViewHolder is VideoViewHolder){
                        videoViewHolder.startVideo()
                    }

                    currentPlayingPosition = currentPosition

                }
            }
        }
    }


    private fun stopVideo(){

    }

    private fun getCurrentItem():Int{
        if(rvVideo!=null){
            return (rvVideo.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }
        return -1
    }


    /*private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            playerView?.setPlayer(player)
            player.setPlayWhenReady(playWhenReady)
            player.seekTo(currentWindow, playbackPosition)
        }
        val mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_mp4)))
        player.prepare(mediaSource, true, false)
    }


    private fun buildMediaSource(uri: Uri): MediaSource {

        val userAgent = "exoplayer-codelab"

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else {
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER))
            val manifestDataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
            return DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri)
        }
    }

    fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
//            Player.STATE_BUFFERING -> status = PlaybackStatus.LOADING
//            Player.STATE_ENDED -> status = PlaybackStatus.STOPPED
//            Player.STATE_IDLE -> status = PlaybackStatus.IDLE
//            Player.STATE_READY -> status = if (playWhenReady) PlaybackStatus.PLAYING else PlaybackStatus.PAUSED
//            else -> status = PlaybackStatus.IDLE
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition()
            currentWindow = player.getCurrentWindowIndex()
            playWhenReady = player.getPlayWhenReady()
            player.release()
            player = null
        }
    }*/
}
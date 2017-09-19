package com.cmulugeta.mediaplayer.ui.player

import android.content.ComponentName
import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import android.os.SystemClock
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.DateUtils
import android.view.View
import android.widget.SeekBar
import com.google.gson.reflect.TypeToken
import com.cmulugeta.mediaplayer.FitnessSound
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.playback.QueueManager
import com.cmulugeta.mediaplayer.playback.PlaybackManager
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import com.cmulugeta.mediaplayer.playback.MusicPlaybackService
import com.cmulugeta.mediaplayer.ui.utils.BundleUtils
import com.cmulugeta.mediaplayer.ui.utils.Constants
import kotlinx.android.synthetic.main.activity_player.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.graphics.Bitmap
import android.util.Log
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority


class PlayerActivity:AppCompatActivity(){

    private val executorService= Executors.newSingleThreadScheduledExecutor()
    private var scheduledFuture: ScheduledFuture<*>? = null

    private val handler= Handler()
    private val PROGRESS_UPDATE_INTERNAL: Long = 100
    private val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 10
    private var lastState:PlaybackStateCompat?=null

    private val controllerCallback=object:MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            updatePlaybackState(state)
        }
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            updateDuration(metadata)
            updatePicture(metadata)
        }
    }

    private val connectionCallback:MediaBrowserCompat.ConnectionCallback=object:MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            super.onConnected()
            try {
                val mediaController = MediaControllerCompat(this@PlayerActivity, browser?.sessionToken)
                mediaController.registerCallback(controllerCallback)
                MediaControllerCompat.setMediaController(this@PlayerActivity, mediaController)
                inject()
            } catch (ex: RemoteException) {
                ex.printStackTrace()
            }
        }
    }

    private var browser:MediaBrowserCompat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        ButterKnife.bind(this)
        browser=MediaBrowserCompat(this,
                ComponentName(this, MusicPlaybackService::class.java),
                connectionCallback, null)
        progressView.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onStopTrackingTouch(seekBar: SeekBar?){
                seekBar?.let {
                    MediaControllerCompat.getMediaController(this@PlayerActivity).transportControls
                            .seekTo(it.progress.toLong())
                    startSeekBarUpdate()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?)=stopSeekBarUpdate()
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                start_time.text = DateUtils.formatElapsedTime((progress / 1000).toLong())
            }
        })
    }

    @OnClick(R.id.next)
    fun next()=controlls().skipToNext()

    @OnClick(R.id.prev)
    fun prev()=controlls().skipToPrevious()

    @OnClick(R.id.repeat)
    fun repeat()=controlls().setRepeatMode(0)

    @OnClick(R.id.shuffle)
    fun shuffle()=controlls().setShuffleModeEnabled(true)

    @OnClick(R.id.play_pause)
    fun playPause() {
        lastState = null
        val controllerCompat = MediaControllerCompat.getMediaController(this)
        val stateCompat = controllerCompat.playbackState
        if (stateCompat != null) {
            val controls = controllerCompat.transportControls
            when (stateCompat.state) {
                PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.STATE_BUFFERING -> controls.pause()
                PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED -> controls.play()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        browser?.connect()
    }

    override fun onStop() {
        super.onStop()
        browser?.disconnect()
        MediaControllerCompat.getMediaController(this)
                .unregisterCallback(controllerCallback)
    }

    private fun controlls()=MediaControllerCompat.getMediaController(this@PlayerActivity).transportControls

    private fun stopSeekBarUpdate(){
        lastState=null
        scheduledFuture?.cancel(true)
    }

    private fun startSeekBarUpdate(){
        scheduledFuture = executorService.scheduleAtFixedRate({handler.post(this@PlayerActivity::updateProgress)},
                PROGRESS_UPDATE_INITIAL_INTERVAL, PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
    }

    private fun updateProgress(){
        lastState?.let {
            var position=it.position
            if (it.state ==PlaybackStateCompat.STATE_PLAYING) {
                val timeDelta = SystemClock.elapsedRealtime() - it.lastPositionUpdateTime
                position+=(timeDelta.toInt() * it.playbackSpeed).toLong()
            }
            progressView.progress=position.toInt()
            start_time.text=DateUtils.formatElapsedTime(position/1000)
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        stopSeekBarUpdate()
        executorService.shutdown()
    }

    private fun updatePlaybackState(stateCompat: PlaybackStateCompat?) {
        stateCompat?.let {
            lastState = stateCompat
          /*  updateRepeatMode(isActionApplied(stateCompat.actions,
                    PlaybackStateCompat.ACTION_SET_REPEAT_MODE))
            updateShuffleMode(isActionApplied(stateCompat.actions,
                    PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED))   */
            //check the state
            when (stateCompat.state) {
                PlaybackStateCompat.STATE_PLAYING -> {
                    play_pause.visibility= View.VISIBLE
                    if (play_pause.isPlay) {
                        play_pause.change(false, true)
                    }
                    startSeekBarUpdate()
                }
                PlaybackStateCompat.STATE_PAUSED -> {
                    play_pause.visibility= View.VISIBLE
                    if (!play_pause.isPlay) {
                        play_pause.change(true, true)
                    }
                    stopSeekBarUpdate()
                }
                PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                    play_pause.visibility= View.VISIBLE
                    if (play_pause.isPlay) {
                        play_pause.change(false, true)
                    }
                    stopSeekBarUpdate()
                }
                PlaybackStateCompat.STATE_BUFFERING -> {
                    play_pause.visibility= View.INVISIBLE
                    stopSeekBarUpdate()
                }
            }
        }
    }

    private fun updateDuration(metadataCompat: MediaMetadataCompat?) {
        metadataCompat?.let {
            var duration = metadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER).toInt()
            start_time.text=DateUtils.formatElapsedTime((duration / 1000).toLong())
            duration = metadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt()
            end_time.text=DateUtils.formatElapsedTime((duration / 1000).toLong())
            progressView.max=duration
        }
    }

    private fun updatePicture(metadataCompat: MediaMetadataCompat?) {
        metadataCompat?.let {
            val text = java.lang.Long.toString(metadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER))+" of " + java.lang.Long.toString(metadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS))
            track_name.text=metadataCompat.getText(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE)
            artist.text = metadataCompat.getText(MediaMetadataCompat.METADATA_KEY_ARTIST)
            pages.text = text
            val imageUrl = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI)
            showArt(imageUrl)
        }
    }

    private fun showArt(artUrl: String?) {
        Glide.with(this)
                .load(artUrl)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .into(object : ImageViewTarget<Bitmap>(circle) {
                    override fun setResource(resource: Bitmap) {
                        circle.setImageBitmap(resource)
                    }
                })
    }

    fun inject() =FitnessSound.app().playbackComponent().inject(this)

    @Inject
    fun injectManager(manager:PlaybackManager){
        intent?.extras?.let {
            val queue:QueueManager?=BundleUtils.fetchHeavyObject<QueueManager>(object:TypeToken<QueueManager>() {}.type,
                    it,Constants.EXTRA_QUEUE)
            queue?.let {
                manager.setQueueManager(it)
                manager.handleResumeRequest()
                play_pause.change(false)
            }
        }
    }
}

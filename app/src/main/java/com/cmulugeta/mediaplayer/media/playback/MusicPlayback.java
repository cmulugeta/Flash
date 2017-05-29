package com.cmulugeta.mediaplayer.media.playback;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.net.wifi.WifiManager;
import android.support.v4.media.session.PlaybackStateCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MusicPlayback implements IPlayback,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnSeekCompleteListener {

    public static final float VOLUME_DUCK = 0.2f;
    public static final float VOLUME_NORMAL = 1.0f;

    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    private static final int AUDIO_FOCUSED  = 2;

    private int state;
    private int audioFocus=AUDIO_NO_FOCUS_NO_DUCK;
    private boolean focusGained;
    private volatile long currentPosition;
    private volatile String mediaId;
    private final WifiManager.WifiLock wifiLock;
    private Callback callback;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Context context;

    private final BroadcastReceiver mAudioNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                if (isPlaying()) {
                    //send a message to pause the playback
                }
            }
        }
    };


    @Inject
    public MusicPlayback(Context context){
        this.context=context;
        this.audioManager=AudioManager.class.cast(context.getSystemService(Context.AUDIO_SERVICE));
        this.wifiLock=WifiManager.class.cast(context.getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL,"uAmp_lock");
        this.state= PlaybackStateCompat.STATE_NONE;
        registerNoiseReceiver();

    }

    @Override
    public void playNext() {

    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void pause() {
        if(state==PlaybackStateCompat.STATE_PLAYING){
            if(mediaPlayer!=null){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    currentPosition=mediaPlayer.getCurrentPosition();
                }
            }
            releaseFocus();
        }
        state=PlaybackStateCompat.STATE_PAUSED;
        callback.onStateChanged(state);
        unregisterNoiseReceiver();
    }

    @Override
    public void play(QueueItem item) {

    }

    @Override
    public void playPrevious() {

    }

    @Override
    public boolean isPlaying() {
        return focusGained && (mediaPlayer!=null && mediaPlayer.isPlaying());
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setCurrentMediaId(String mediaId) {
        this.mediaId=mediaId;
    }

    @Override
    public void setCurrentStreamPosition(long position) {
        this.currentPosition=position;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public long getCurrentStreamPosition() {
        return currentPosition;
    }

    @Override
    public String getCurrentMediaId() {
        return null;
    }

    @Override
    public void setState(int state) {
        this.state=state;
    }

    @Override
    public void seekTo(long position) {
        currentPosition=position;
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                state=PlaybackStateCompat.STATE_BUFFERING;
            }
            registerNoiseReceiver();
            mediaPlayer.seekTo((int)position);
            callback.onStateChanged(state);
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer player) {
        currentPosition = player.getCurrentPosition();
        if (state == PlaybackStateCompat.STATE_BUFFERING) {
            registerNoiseReceiver();
            mediaPlayer.start();
            state = PlaybackStateCompat.STATE_PLAYING;
        }
        callback.onStateChanged(state);
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        callback.onFinished();
    }

    @Override
    public boolean onError(MediaPlayer player, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        configMediaPlayer();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            audioFocus = AUDIO_FOCUSED;
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            boolean canDuck = focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
            audioFocus = canDuck ? AUDIO_NO_FOCUS_CAN_DUCK : AUDIO_NO_FOCUS_NO_DUCK;
            if (state == PlaybackStateCompat.STATE_PLAYING && !canDuck) {
                focusGained=true;
            }
        }
        configMediaPlayer();
    }

    @Override
    public void stop() {
        state=PlaybackStateCompat.STATE_STOPPED;
        if(callback!=null) callback.onStateChanged(state);
        currentPosition=getCurrentStreamPosition();
        releaseFocus();
        releasePlayer();
        releaseWifiLock();
        unregisterNoiseReceiver();
    }

    private void gainAudioFocus(){
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            audioFocus = AUDIO_FOCUSED;
        } else {
            audioFocus = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    private void configMediaPlayer(){
        if(audioFocus==AUDIO_NO_FOCUS_NO_DUCK){
            if(state==PlaybackStateCompat.STATE_PLAYING){
                pause();
            }
        }else{
            registerNoiseReceiver();
            final float volume=audioFocus==AUDIO_NO_FOCUS_CAN_DUCK
                    ? VOLUME_DUCK:VOLUME_NORMAL;
            if(mediaPlayer!=null) mediaPlayer.setVolume(volume,volume);

            if(focusGained){
                if(mediaPlayer!=null){
                    if(!mediaPlayer.isPlaying()){
                        if (currentPosition == mediaPlayer.getCurrentPosition()) {
                            mediaPlayer.start();
                            state = PlaybackStateCompat.STATE_PLAYING;
                        } else {
                            mediaPlayer.seekTo((int)currentPosition);
                            state = PlaybackStateCompat.STATE_BUFFERING;
                        }
                    }
                }
                focusGained=false;
            }
            callback.onStateChanged(state);
        }
    }

    private void createMediaPlayer(){
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setWakeMode(context.getApplicationContext(),
                    PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
        }else{
            mediaPlayer.reset();
        }
    }

    private void releaseFocus(){
        if (audioManager.abandonAudioFocus(this) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            audioFocus = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    private void releaseWifiLock(){
        if(wifiLock.isHeld()) wifiLock.release();
    }

    private void releasePlayer(){
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }


    private void registerNoiseReceiver(){

    }

    private void unregisterNoiseReceiver(){

    }

    @Override
    public void start() {

    }
}

package com.cmulugeta.mediaplayer.media.playback;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.cmulugeta.mediaplayer.media.service.MusicPlaybackService;

import static dagger.internal.Preconditions.checkNotNull;

public abstract class BasePlayback implements Playback,
        AudioManager.OnAudioFocusChangeListener{

    private static final String TAG=BasePlayback.class.getSimpleName();

    public static final float VOLUME_DUCK = 0.2f;
    public static final float VOLUME_NORMAL = 1.0f;

    public static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    public static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    public static final int AUDIO_FOCUSED  = 2;

    protected Callback callback;
    protected  WifiManager.WifiLock wifiLock;
    protected int focusState=AUDIO_NO_FOCUS_NO_DUCK;
    private boolean noisyReceiverRegistered;
    private AudioManager audioManager;
    protected Context context;
    protected volatile String currentUrl;

    private final IntentFilter audioBecomingNoisyIntent=
            new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

    private final BroadcastReceiver audioNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                if (isPlaying()) {
                    Log.d(TAG,"Headphones disconnected");
                    Intent i=new Intent(context, MusicPlaybackService.class);
                    i.setAction(MusicPlaybackService.ACTION_CMD);
                    i.putExtra(MusicPlaybackService.CMD_NAME,MusicPlaybackService.CMD_PAUSE);
                    context.startService(i);
                }
            }
        }
    };

    public BasePlayback(Context context,
                        AudioManager audioManager,
                        WifiManager.WifiLock wifiLock){
        this.context=checkNotNull(context);
        this.wifiLock=checkNotNull(wifiLock);
        this.audioManager=checkNotNull(audioManager);
    }

    @Override
    public void play(String mediaUrl) {
        if(!TextUtils.isEmpty(mediaUrl)) {
            requestFocus();
            registerNoiseReceiver();
            acquireWifiLock();
            if(TextUtils.equals(mediaUrl,currentUrl)){
                Log.d(TAG,"Playing the same");
                resumePlayer();
                return;
            }
            Log.d(TAG,"Playing different");
            this.currentUrl=mediaUrl;
            startPlayer();
        }
    }

    public abstract void startPlayer();
    public abstract void stopPlayer();
    public abstract void pausePlayer();
    public abstract void resumePlayer();
    public abstract void updatePlayer();

    protected void acquireWifiLock(){
        wifiLock.acquire();
    }

    protected void requestFocus(){
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            focusState = AUDIO_FOCUSED;
        } else {
            focusState = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            focusState = AUDIO_FOCUSED;
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            boolean canDuck = focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
            focusState = canDuck ? AUDIO_NO_FOCUS_CAN_DUCK : AUDIO_NO_FOCUS_NO_DUCK;
        }
        Log.d(TAG,"Focus changed");
        updatePlayer();
    }

    private void releaseFocus(){
        audioManager.abandonAudioFocus(this);
        focusState=AUDIO_NO_FOCUS_NO_DUCK;
    }

    private void releaseWifiLock(){
        if(wifiLock.isHeld()){
            wifiLock.release();
        }
    }

    protected void registerNoiseReceiver(){
        if(!noisyReceiverRegistered){
            context.registerReceiver(audioNoisyReceiver,audioBecomingNoisyIntent);
            noisyReceiverRegistered=true;
        }
    }

    protected void unregisterNoiseReceiver(){
        if(noisyReceiverRegistered){
            context.unregisterReceiver(audioNoisyReceiver);
            noisyReceiverRegistered=false;
        }
    }

    @Override
    public void pause() {
        Log.d(TAG,"Will pause");
        pausePlayer();
        unregisterNoiseReceiver();
        releaseWifiLock();
        if(callback!=null) callback.onPause();
    }

    @Override
    public void stop() {
        Log.d(TAG,"Stopping");
        releaseFocus();
        releaseWifiLock();
        unregisterNoiseReceiver();
        stopPlayer();
        if(callback!=null){
            callback.onStop();
        }
    }

    @Override
    public void setCallback(Playback.Callback callback) {
        this.callback = callback;
    }
}
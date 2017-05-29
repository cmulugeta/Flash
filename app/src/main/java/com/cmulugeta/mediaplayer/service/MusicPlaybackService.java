package com.cmulugeta.mediaplayer.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import java.util.List;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.cmulugeta.mediaplayer.playback.PlaybackManager;


public class MusicPlaybackService extends MediaBrowserServiceCompat
            implements PlaybackManager.PlaybackManagerCallback{

    private static final String LOG_TAG=MusicPlaybackService.class.getSimpleName();

    public static final String MEDIA_ID_ROOT="root";
    public static final String MEDIA_ID_EMPTY_ROOT="empty_root";

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;


    @Override
    public void onCreate() {
        super.onCreate();

        mediaSession = new MediaSessionCompat(getApplicationContext(), LOG_TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        setSessionToken(mediaSession.getSessionToken());

    }

    @Override
    public void onPlaybackStart() {
        mediaSession.setActive(true);
        Intent intent=new Intent(this,MusicPlaybackService.class);
        startService(intent);
    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {

    }

    @Override
    public void onNotificationRequired() {

    }

    @Override
    public void onPlaybackStop() {
        mediaSession.setActive(false);
        stopSelf();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        Log.d(LOG_TAG,"onGetRoot()");
        if(!clientPackageName.equals(getPackageName())){
            return new BrowserRoot(MEDIA_ID_ROOT,null);
        }
        return new BrowserRoot(MEDIA_ID_EMPTY_ROOT,null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.d(LOG_TAG,"onLoadChildren()");
    }


}

package com.cmulugeta.mediaplayer.media.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.service.media.MediaBrowserService;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cmulugeta.mediaplayer.MainActivity;
import com.cmulugeta.mediaplayer.R;

public class MusicNotification {

    public static final int NOTIFICATION_ID = 412;

    private static final int PLAYER_PENDING_INTENT_ID=3405;
    private static final int PAUSE_PENDING_INTENT_ID=3406;
    private static final int PLAY_PENDING_INTENT_ID=3407;
    private static final int PLAY_NEXT_PENDING_INTENT_ID=3408;
    private static final int PLAY_PREV_PENDING_INTENT_ID=3409;

    public static final String ACTION_PAUSE = "com.cmulugeta.player.pause";
    public static final String ACTION_PLAY = "com.cmulugeta.player.play";
    public static final String ACTION_PREV = "com.cmulugeta.player.prev";
    public static final String ACTION_NEXT = "com.cmulugeta.player.next";


    private PlaybackStateCompat playbackState;
    private MediaMetadataCompat mediaMetadata;
    private NotificationManagerCompat manager;
    private boolean isStarted;
    private MediaSessionCompat.Token token;
    private MediaBrowserServiceCompat service;

    public MusicNotification(MediaBrowserServiceCompat service){
        this.service=service;
        this.manager=NotificationManagerCompat.from(service);
        this.token=service.getSessionToken();
        manager.cancel(NOTIFICATION_ID);
    }

    public void updatePlaybackState(PlaybackStateCompat playbackState){
        this.playbackState=playbackState;
        if(playbackState.getState()==PlaybackStateCompat.STATE_STOPPED||
                playbackState.getState()==PlaybackStateCompat.STATE_NONE){
            stopNotification();
        }else{
            updateNotification();
        }
    }

    public void startNotification(){
        if(!isStarted){
            Notification notification=createNotification();
            if(notification!=null) {
                service.startForeground(NOTIFICATION_ID,notification);
                isStarted=true;
            }
        }
    }

    public void updateMetadata(MediaMetadataCompat metadata){
        this.mediaMetadata=metadata;
        updateNotification();
    }

    private void updateNotification(){
        if(!isStarted){
            startNotification();
        }else {
            Notification notification = createNotification();
            if (notification != null) {
                manager.notify(NOTIFICATION_ID, notification);
            }
        }
    }

    private void stopNotification(){
        if(isStarted){
            isStarted=false;
            service.stopForeground(true);
        }
    }

    private PendingIntent contentIntent(Context context){
        Intent startActivityIntent=new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context,
                PLAYER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private NotificationCompat.Action pause(Context context){
        Intent playPauseIntent=new Intent(context,MusicPlaybackService.class);
        playPauseIntent.setAction(ACTION_PAUSE);

        PendingIntent pausePendingIntent=PendingIntent.getBroadcast(context,
                PAUSE_PENDING_INTENT_ID,
                playPauseIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_pause_notif,"Pause",pausePendingIntent);
    }

    private NotificationCompat.Action next(Context context){
        Intent nextIntent=new Intent(context,MusicPlaybackService.class);
        nextIntent.setAction(ACTION_NEXT);

        PendingIntent nextPendingIntent=PendingIntent.getBroadcast(context,
                PLAY_NEXT_PENDING_INTENT_ID,
                nextIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_skip_next_notif,"Next",nextPendingIntent);
    }

    private NotificationCompat.Action prev(Context context){
        Intent prevIntent=new Intent(context,MusicPlaybackService.class);
        prevIntent.setAction(ACTION_PREV);

        PendingIntent prevPendingIntent=PendingIntent.getBroadcast(context,
                PLAY_PREV_PENDING_INTENT_ID,
                prevIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_skip_prev_notif,"Previous",prevPendingIntent);
    }

    private NotificationCompat.Action play(Context context){
        Intent prevIntent=new Intent(context,MusicPlaybackService.class);
        prevIntent.setAction(ACTION_PLAY);

        PendingIntent prevPendingIntent=PendingIntent.getService(context,
                PLAY_PENDING_INTENT_ID,
                prevIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_play_notif,"Play",prevPendingIntent);
    }

    private Notification createNotification(){
        if(mediaMetadata==null||playbackState==null) return null;
        NotificationCompat.Builder builder=new NotificationCompat.Builder(service);
        builder.setStyle(new  NotificationCompat.MediaStyle()
                .setMediaSession(token)
                .setShowActionsInCompactView(1))
                .setColor(Color.WHITE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setSmallIcon(R.drawable.ic_play)
                .setContentIntent(contentIntent(service))
                .setContentTitle(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
                .setContentText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
                .addAction(prev(service));
        if(playbackState.getState()==PlaybackStateCompat.STATE_PLAYING){
            builder.addAction(pause(service));
        }else{
            builder.addAction(play(service));
        }
        builder.addAction(next(service));
        setNotificationPlaybackState(builder);
        loadImage(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI),builder);
        return builder.build();
    }

    private void setNotificationPlaybackState(NotificationCompat.Builder builder) {
        if (playbackState == null || !isStarted) {
            service.stopForeground(true);
            return;
        }
        if (playbackState.getState() == PlaybackStateCompat.STATE_PLAYING
                && playbackState.getPosition() >= 0) {
            builder.setWhen(System.currentTimeMillis() - playbackState.getPosition())
                    .setShowWhen(true)
                    .setUsesChronometer(true);
        } else {
            builder.setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false);
        }
        builder.setOngoing(playbackState.getState() == PlaybackStateCompat.STATE_PLAYING);
    }

    private void loadImage(String imageUrl, NotificationCompat.Builder builder){
        if(!TextUtils.isEmpty(imageUrl)) {
            Glide.with(service)
                    .load(imageUrl)
                    .asBitmap()
                    .priority(Priority.IMMEDIATE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            builder.setLargeIcon(resource);
                            manager.notify(NOTIFICATION_ID, builder.build());
                        }
                    });
        }
    }

}

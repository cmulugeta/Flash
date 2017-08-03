package com.cmulugeta.mediaplayer.media.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationManager {

    private static final int NOTIFICATION_ID = 412;
    private static final int REQUEST_CODE = 100;

    public static final String ACTION_PAUSE = "com.cmulugeta.player.pause";
    public static final String ACTION_PLAY = "com.cmulugeta.player.play";
    public static final String ACTION_PREV = "com.cmulugeta.player.prev";
    public static final String ACTION_NEXT = "com.cmulugeta.player.next";
    public static final String ACTION_STOP_CASTING = "com.cmulugeta.player.stop_casting";


}

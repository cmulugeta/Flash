package com.cmulugeta.mediaplayer.media.service;

import com.cmulugeta.mediaplayer.media.playback.PlaybackManager;

public class MediaTasks {

    public static final String ACTION_PAUSE = "com.cmulugeta.player.pause";
    public static final String ACTION_PLAY = "com.cmulugeta.player.play";
    public static final String ACTION_PREV = "com.cmulugeta.player.prev";
    public static final String ACTION_NEXT = "com.cmulugeta.player.next";
    public static final String ACTION_STOP_CASTING = "com.cmulugeta.player.stop_casting";

    public static final String ACTION_CMD="action:cmd";
    public static final String CMD_NAME="cmd:name";
    public static final String CMD_PAUSE="cmd:pause";


    public static void executeTask(PlaybackManager playbackManager, String action){

    }

}

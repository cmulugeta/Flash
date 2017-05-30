package com.cmulugeta.mediaplayer.media.model;

import android.support.v4.media.MediaMetadataCompat;

public interface MediaProvider<Q> {
    MediaMetadataCompat at(int index);
    MediaMetadataCompat byId(String mediaId);
    MediaMetadataCompat search(Q queryParameter, String query);
    boolean isInitialized();
    int count();
}

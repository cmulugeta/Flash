package com.cmulugeta.mediaplayer.playback;


import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueManager {

    private int currentIndex;
    private MetadataUpdateListener updateListener;
    private List<MediaSessionCompat.QueueItem> audioQueue;

    public QueueManager(@NonNull MetadataUpdateListener listener){
        this.updateListener=listener;
        this.audioQueue= Collections.synchronizedList(new ArrayList<MediaSessionCompat.QueueItem>());
        this.currentIndex=0;
    }

    public void setCurrentIndex(int index){
        if(checkForRange(index)){
            this.currentIndex=index;
        }
    }

    public boolean skipQueuePosition(int amount){
        final int index=currentIndex+amount;
        this.currentIndex=index<0||audioQueue==null?index:index % audioQueue.size();
        return checkForRange(index);
    }

    public MediaSessionCompat.QueueItem getCurrent(){
        return checkForRange(currentIndex)?audioQueue.get(currentIndex):null;
    }

    public int size(){
        return audioQueue!=null?audioQueue.size():0;
    }

    public void setCurrentItem(String mediaId){

    }

    public void updateMetadata(){
        MediaSessionCompat.QueueItem currentItem=getCurrent();
        if(currentItem!=null){
            updateListener.onMetadataRetrieveError();
            return;
        }


    }

    private boolean checkForRange(int index){
        return !(index<0||audioQueue==null||audioQueue.size()<=index);
    }


    private interface MetadataUpdateListener{
        void onMetadataChanged(MediaMetadataCompat metadata);
        void onMetadataRetrieveError();
        void onCurrentQueueIndexUpdated(int queueIndex);
        void onQueueUpdated(String title, List<MediaSessionCompat.QueueItem> newQueue);
    }
}

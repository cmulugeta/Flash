package com.cmulugeta.mediaplayer;

import android.app.Application;
import com.cmulugeta.mediaplayer.di.component.ApplicationComponent;
import com.cmulugeta.mediaplayer.di.component.DaggerApplicationComponent;
import com.cmulugeta.mediaplayer.di.component.DaggerPlaybackComponent;
import com.cmulugeta.mediaplayer.di.component.PlaybackComponent;
import com.cmulugeta.mediaplayer.di.module.ApplicationModule;
import com.cmulugeta.mediaplayer.di.module.DataModule;
import com.cmulugeta.mediaplayer.di.module.InteractorModule;
import com.cmulugeta.mediaplayer.di.module.MediaPlayerModule;
import com.cmulugeta.mediaplayer.di.module.NetworkModule;

public class FlashApp extends Application {

    private static FlashApp instance;
    private ApplicationComponent component;
    private PlaybackComponent playbackComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initialize();
    }

    private void initialize(){
        component= DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .interactorModule(new InteractorModule())
                .networkModule(new NetworkModule(null))
                .build();
        playbackComponent= DaggerPlaybackComponent.builder()
                .applicationComponent(component)
                .mediaPlayerModule(new MediaPlayerModule())
                .build();
    }

    public static FlashApp app(){
        return instance;
    }

    public ApplicationComponent component(){
        return component;
    }

    public PlaybackComponent playbackComponent(){
        return playbackComponent;
    }

}

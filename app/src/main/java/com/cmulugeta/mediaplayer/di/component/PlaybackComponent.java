package com.cmulugeta.mediaplayer.di.component;

import com.cmulugeta.mediaplayer.di.module.MediaPlayerModule;
import com.cmulugeta.mediaplayer.domain.playback.PlaybackScope;
import com.cmulugeta.mediaplayer.playback.MusicPlaybackService;

import dagger.Component;

@PlaybackScope
@Component(dependencies = ApplicationComponent.class,
    modules = MediaPlayerModule.class)
public interface PlaybackComponent {
    void inject(MusicPlaybackService service);
}

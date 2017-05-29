package com.cmulugeta.mediaplayer.di.component;


import android.content.Context;

import com.cmulugeta.mediaplayer.di.module.ApplicationModule;
import com.cmulugeta.mediaplayer.di.module.MediaPlayerModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class, MediaPlayerModule.class})
public interface ApplicationComponent {
    Context context();

}

package com.cmulugeta.mediaplayer.di.component;


import android.content.Context;

import com.cmulugeta.mediaplayer.di.module.ApplicationModule;
import com.cmulugeta.mediaplayer.di.module.MediaPlayerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context context();
}

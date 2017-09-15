package com.cmulugeta.mediaplayer;

import android.app.Application;
import com.cmulugeta.mediaplayer.di.component.ApplicationComponent;
import com.cmulugeta.mediaplayer.di.component.DaggerApplicationComponent;
import com.cmulugeta.mediaplayer.di.module.ApplicationModule;
import com.cmulugeta.mediaplayer.di.module.DataModule;
import com.cmulugeta.mediaplayer.di.module.InteractorModule;
import com.cmulugeta.mediaplayer.di.module.NetworkModule;

public class FitnessSound extends Application {

    private static FitnessSound instance;
    private ApplicationComponent component;

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
    }

    public static FitnessSound app(){
        return instance;
    }

    public ApplicationComponent component(){
        return component;
    }

}

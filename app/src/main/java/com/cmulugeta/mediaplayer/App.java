package com.cmulugeta.mediaplayer;

import android.app.Application;

import com.cmulugeta.mediaplayer.di.component.ApplicationComponent;

public class App extends Application{

    private static App instance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    private void initializeComponent(){

    }
    public static App app(){
        return instance;
    }

}

package com.cmulugeta.mediaplayer

import android.app.Application

import com.cmulugeta.mediaplayer.di.component.ApplicationComponent
import com.cmulugeta.mediaplayer.di.component.DaggerApplicationComponent
import com.cmulugeta.mediaplayer.di.module.ApplicationModule
import com.cmulugeta.mediaplayer.di.module.DataModule
import com.cmulugeta.mediaplayer.di.module.InteractorModule
import com.cmulugeta.mediaplayer.di.module.NetworkModule

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
    }

    private fun initializeComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .interactorModule(InteractorModule())
                .networkModule(NetworkModule(null))
                .build()
    }
}

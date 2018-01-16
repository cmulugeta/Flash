package com.cmulugeta.mediaplayer

import android.app.Application
import com.cmulugeta.mediaplayer.di.component.ApplicationComponent
import com.cmulugeta.mediaplayer.di.component.DaggerApplicationComponent
import com.cmulugeta.mediaplayer.di.component.DaggerPlaybackComponent
import com.cmulugeta.mediaplayer.di.component.PlaybackComponent
import com.cmulugeta.mediaplayer.di.module.*

class App : Application() {
  val component: ApplicationComponent by lazy(LazyThreadSafetyMode.NONE){
    DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .dataModule(DataModule())
        .interactorModule(InteractorModule())
        .networkModule(NetworkModule(null))
        .build()
  }

  val playbackComponent: PlaybackComponent by lazy(LazyThreadSafetyMode.NONE){
    DaggerPlaybackComponent.builder()
        .applicationComponent(component)
        .mediaPlayerModule(MediaPlayerModule())
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  companion object {
    private var instance: App? = null
    val component by lazy(LazyThreadSafetyMode.NONE) {
      instance?.component
    }

    val playbackComponent by lazy(LazyThreadSafetyMode.NONE) {
      instance?.playbackComponent
    }
  }
}
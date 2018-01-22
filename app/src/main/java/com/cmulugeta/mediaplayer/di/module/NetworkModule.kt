package com.cmulugeta.mediaplayer.di.module

import android.content.Context
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.cmulugeta.mediaplayer.CLIENT_ID
import com.cmulugeta.soundcloud.SoundCloud
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.Token
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule constructor(private val token: Token?) {
  @Singleton
  @Provides
  internal fun service(context: Context): SoundCloudService =
      SoundCloud.Builder(context, CLIENT_ID)
          .setInterceptor(OkLogInterceptor.builder()
              .setBaseUrl("http://oklog.responseecho.com")
              .build())
          .setToken(token).build().soundCloudService

}

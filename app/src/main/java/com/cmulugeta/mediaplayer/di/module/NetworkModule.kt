package com.cmulugeta.mediaplayer.di.module

import android.content.Context
import com.cmulugeta.mediaplayer.Config
import com.cmulugeta.soundcloud.SoundCloud
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.Token
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule constructor(val token:Token){
    @Singleton
    @Provides
    fun service(context:Context):SoundCloudService=
            SoundCloud.create(Config.CLIENT_ID)
            .appendToken(token)
            .createService(context)
}

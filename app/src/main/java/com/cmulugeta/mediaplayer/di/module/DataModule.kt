package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.data.Filter
import com.cmulugeta.mediaplayer.data.MusicRepository
import com.cmulugeta.mediaplayer.data.local.MusicDatabase
import com.cmulugeta.mediaplayer.data.local.TrackHandler
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.data.mapper.TrackMapper
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.model.TrackEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun repository(repository:MusicRepository):Repository=repository

    @Singleton
    @Provides
    fun mapper(mapper:TrackMapper): Mapper<Track, TrackEntity> =mapper

    @Singleton
    @Provides
    fun filter()=Filter()

    @Singleton
    @Provides
    fun handler(helper: MusicDatabase)=TrackHandler(helper)
}
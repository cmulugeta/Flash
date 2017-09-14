package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.data.MusicRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun repository(repository:MusicRepository)=repository
}
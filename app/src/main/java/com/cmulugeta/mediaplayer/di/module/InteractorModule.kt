package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.interactor.LikeTrack
import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.domain.interactor.SearchTracks
import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule{
    @Singleton
    @Provides
    fun likeInteractor(repository:Repository, scheduler: BaseScheduler) =LikeTrack(repository,scheduler)

    @Singleton
    @Provides
    fun lovedInteractor(repository:Repository, scheduler: BaseScheduler) =LovedTracks(repository,scheduler)

    @Singleton
    @Provides
    fun searchInteractor(repository:Repository, scheduler: BaseScheduler) = SearchTracks(repository,scheduler)

    @Singleton
    @Provides
    fun historyInteractor(repository:Repository, scheduler: BaseScheduler) =TrackHistory(repository,scheduler)
}

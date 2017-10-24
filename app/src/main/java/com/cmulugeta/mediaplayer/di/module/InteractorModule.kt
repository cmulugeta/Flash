package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.interactor.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule{
    @Singleton
    @Provides
    fun modifyInteractor(repository: Repository, scheduler: BaseScheduler)=ModifyTracks(repository,scheduler)

    @Singleton
    @Provides
    fun searchInteractor(repository:Repository, scheduler: BaseScheduler)= SearchTracks(repository,scheduler)

    @Singleton
    @Provides
    fun tracksInteractor(repository:Repository, scheduler: BaseScheduler) =GetTracks(repository,scheduler)
}

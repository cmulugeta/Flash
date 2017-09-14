package com.cmulugeta.mediaplayer.di.component

import android.content.Context
import com.cmulugeta.mediaplayer.di.module.ApplicationModule
import com.cmulugeta.mediaplayer.di.module.DataModule
import com.cmulugeta.mediaplayer.di.module.InteractorModule
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.interactor.LikeTrack
import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.domain.interactor.SearchTracks
import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import com.cmulugeta.soundcloud.SoundCloudService
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class,
        InteractorModule::class, DataModule::class))
interface ApplicationComponent {
    fun inject(activity:BaseActivity)
    fun context(): Context
    fun scheduler():BaseScheduler
    fun repository():Repository
    fun likeInteractor():LikeTrack
    fun lovedInteractor():LovedTracks
    fun searchInteractor():SearchTracks
    fun historyInteractor():TrackHistory
    fun service():SoundCloudService
}
package com.cmulugeta.mediaplayer.di.component

import android.content.Context
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.di.module.ApplicationModule
import com.cmulugeta.mediaplayer.di.module.DataModule
import com.cmulugeta.mediaplayer.di.module.InteractorModule
import com.cmulugeta.mediaplayer.di.module.NetworkModule
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.interactor.*
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import com.cmulugeta.mediaplayer.ui.base.Navigator
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.TrackEntity
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class,
    InteractorModule::class,
    DataModule::class,
    NetworkModule::class))
interface ApplicationComponent {
  fun inject(activity: BaseActivity)
  fun context(): Context
  fun scheduler(): BaseScheduler
  fun mapper(): Mapper<Track, TrackEntity>
  fun navigator(): Navigator
  fun repository(): Repository
  fun modifyInteractor(): ModifyTracks
  fun searchInteractor(): SearchTracks
  fun singleInteractor(): GetTracks
  fun service(): SoundCloudService
}
package com.cmulugeta.mediaplayer.di

import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.support.v4.media.MediaMetadataCompat
import com.cmulugeta.mediaplayer.CLIENT_ID
import com.cmulugeta.mediaplayer.data.Filter
import com.cmulugeta.mediaplayer.data.MusicRepository
import com.cmulugeta.mediaplayer.data.local.MusicDatabase
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.data.mapper.TrackMapper
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.domain.interactor.GetTracks
import com.cmulugeta.mediaplayer.domain.interactor.ModifyTracks
import com.cmulugeta.mediaplayer.domain.interactor.SearchTracks
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.playback.Playback
import com.cmulugeta.mediaplayer.playback.MediaPlayback21
import com.cmulugeta.mediaplayer.playback.MetadataMapper
import com.cmulugeta.mediaplayer.ui.base.Navigator
import com.cmulugeta.mediaplayer.ui.details.ActionsPresenter
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.favorite.FavoritePresenter
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.search.SearchContract
import com.cmulugeta.mediaplayer.ui.search.TrackPresenter
import com.cmulugeta.soundcloud.SoundCloud
import com.cmulugeta.soundcloud.model.TrackEntity
import org.koin.dsl.module.applicationContext

val general = applicationContext {
  bean { Navigator() }
  bean { SchedulerProvider() } bind BaseScheduler::class
}

val dataProviders = applicationContext {
  bean { Filter() }
  bean { MusicDatabase(get()) }
  bean {
    MusicRepository(get(), get(), get(), get(), get())
  } bind Repository::class
}

val network = applicationContext {
  SoundCloud.Builder(get(), CLIENT_ID)
      .setToken(get(KoinParams.Token.toString())).build()
      .soundCloudService
}

val mappers = applicationContext {
  factory { TrackMapper() as Mapper<Track, TrackEntity> }
  factory { MetadataMapper() as Mapper<MediaMetadataCompat, Track> }
}

val presenters = applicationContext {
  factory { ModifyTracks(get(), get()) }
  factory { SearchTracks(get(), get()) }
  factory { GetTracks(get(), get()) }
  factory { HistoryPresenter(get(), get()) } bind HomeContract.Presenter::class
  factory { FavoritePresenter(get(), get()) } bind HomeContract.Presenter::class
  bean { ActionsPresenter(get()) } bind ActionsPresenter::class
  bean { TrackPresenter(get()) as SearchContract.Presenter<Track> }
  bean { TrackPresenter(get()) as SearchContract.Presenter<Track> }
}

val mediaPlayer = applicationContext {
  bean { playback(get()) }
}

private fun playback(context: Context): Playback {
  val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
  val wifiManager = (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
      .createWifiLock(WifiManager.WIFI_MODE_FULL, "uAmp_lock")
  return MediaPlayback21(context, audioManager, wifiManager)
}

sealed class KoinParams {
  object Token : KoinParams() {
    override fun toString() = "token"
  }
}
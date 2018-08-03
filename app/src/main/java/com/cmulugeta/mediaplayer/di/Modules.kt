package com.cmulugeta.mediaplayer.di

import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import com.cmulugeta.mediaplayer.CLIENT_ID
import com.cmulugeta.mediaplayer.data.AppPreferences
import com.cmulugeta.mediaplayer.data.Filter
import com.cmulugeta.mediaplayer.data.MusicRepository
import com.cmulugeta.mediaplayer.data.local.MusicDatabase
import com.cmulugeta.mediaplayer.data.local.TrackHandler
import com.cmulugeta.mediaplayer.data.mapper.TrackMapper
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.domain.interactor.*
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.playback.Playback
import com.cmulugeta.mediaplayer.playback.MediaPlayback21
import com.cmulugeta.mediaplayer.playback.MetadataMapper
import com.cmulugeta.mediaplayer.playback.PlaybackManager
import com.cmulugeta.mediaplayer.ui.base.Navigator
import com.cmulugeta.mediaplayer.ui.details.ActionsPresenter
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.favorite.FavoritePresenter
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.search.SearchContract
import com.cmulugeta.mediaplayer.ui.search.TrackPresenter
import com.cmulugeta.mediaplayer.ui.settings.SettingsContract
import com.cmulugeta.mediaplayer.ui.settings.SettingsPresenter
import com.cmulugeta.soundcloud.SoundCloud
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext


val general = applicationContext {
  bean { androidApplication() } bind Context::class
  bean { Navigator() }
  bean { SchedulerProvider() } bind BaseScheduler::class
}

val dataProviders = applicationContext {
  bean { ErrorHandler() }
  bean { Filter(get()) }
  bean { MusicDatabase(get()) }
  bean { AppPreferences() } bind PreferencesInteractor::class
  bean { TrackHandler(get<MusicDatabase>()) }
  bean {
    MusicRepository(get<TrackMapper>(), get(),
            get(), get(), get())
  } bind Repository::class
}

val network = applicationContext {
  bean {
    SoundCloud.Builder(get(), CLIENT_ID)
            .setToken(null).build()
            .soundCloudService
  }
}

val mappers = applicationContext {
  factory { TrackMapper() }
  factory { MetadataMapper() }
}

val presenters = applicationContext {
  bean { ModifyTracks(get(), get(), get()) }
  bean { SearchTracks(get(), get(), get()) }
  bean { GetTracks(get(), get(), get()) }

  factory(Params.HISTORY) { arguments ->
    HistoryPresenter(get<GetTracks>(),
            get<ModifyTracks>(), arguments[Params.HISTORY])
  } bind HomeContract.Presenter::class

  factory(Params.FAVORITE) { arguments ->
    FavoritePresenter(get<GetTracks>(),
            get<ModifyTracks>(), arguments[Params.FAVORITE])
  } bind HomeContract.Presenter::class

  factory(Params.ACTIONS) { arguments ->
    ActionsPresenter(get(), arguments[Params.ACTIONS])
  } bind ActionsPresenter::class

  factory(Params.SEARCH) { arguments ->
    TrackPresenter(get<SearchTracks>(),
            arguments[Params.SEARCH]) as SearchContract.Presenter<Track>
  }

  factory(Params.SETTINGS) { arguments ->
    SettingsPresenter(get(), get(),
            arguments[Params.SETTINGS]) as SettingsContract.Presenter
  }

  bean {
    PlaybackManager(get(), get(), get<ModifyTracks>(), get<MetadataMapper>())
  }
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


object Params {
  const val HISTORY = "history"
  const val FAVORITE = "favorite"
  const val SEARCH = "search"
  const val ACTIONS = "actions"
  const val SETTINGS = "settings"
}
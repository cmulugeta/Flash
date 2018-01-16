package com.cmulugeta.mediaplayer.di.component

import com.cmulugeta.mediaplayer.di.module.MediaPlayerModule
import com.cmulugeta.mediaplayer.playback.MusicPlaybackService
import com.cmulugeta.mediaplayer.ui.player.PlayerActivity
import com.cmulugeta.mediaplayer.domain.playback.PlaybackScope
import dagger.Component

@PlaybackScope
@Component(dependencies = [(ApplicationComponent::class)], modules = [(MediaPlayerModule::class)])
interface PlaybackComponent {
  fun inject(service: MusicPlaybackService)
  fun inject(activity: PlayerActivity)
}

package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.kotlin_extensions.then
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.wrongArgument
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTracks @Inject constructor(val repository: Repository, scheduler: BaseScheduler)
  : SingleInteractor<TrackType, List<Track>>(scheduler) {

  override fun buildSingle(request: TrackType?)
      = request then (repository::fetch) ?: wrongArgument()
}
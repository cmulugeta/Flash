package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.kotlin_extensions.then
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.wrongArgument

class GetTracks(
    private val repository: Repository,
    scheduler: BaseScheduler,
    errorHandler: ErrorHandler
) : SingleInteractor<TrackType, List<Track>>(scheduler, errorHandler) {

  override fun buildSingle(request: TrackType?)
      = request then (repository::fetch) ?: wrongArgument()
}
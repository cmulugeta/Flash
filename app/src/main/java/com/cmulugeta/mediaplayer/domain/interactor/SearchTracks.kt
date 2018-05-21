package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.kotlin_extensions.then
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.wrongArgument

class SearchTracks(
    private val repository: Repository,
    scheduler: BaseScheduler,
    errorHandler: ErrorHandler
) : SingleInteractor<SearchPage, List<Track>>(scheduler, errorHandler) {

  override fun buildSingle(request: SearchPage?)
      = request then (repository::search) ?: wrongArgument()
}
package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.notNullThen
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTracks @Inject constructor(repository: Repository, scheduler: BaseScheduler)
  : SingleInteractor<TrackType>(repository, scheduler) {

  override fun buildCase(params: TrackType?)
      = params notNullThen (repository::fetch)
      ?: Single.error(IllegalArgumentException())
}
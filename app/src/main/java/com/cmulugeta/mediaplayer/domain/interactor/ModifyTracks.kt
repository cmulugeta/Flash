package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest

class ModifyTracks(
    private val repository: Repository,
    private val scheduler: BaseScheduler
) : ModifyInteractor {

  override fun insert(success: () -> Unit, error: (Throwable) -> Unit, request: ModifyRequest) {
    repository.insert(request)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, error)
  }

  override fun clearAll(success: () -> Unit, error: (Throwable) -> Unit, type: TrackType) {
    repository.clearAll(type)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, error)
  }

  override fun remove(success: () -> Unit, error: (Throwable) -> Unit, request: ModifyRequest) {
    repository.remove(request)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, error)
  }
}
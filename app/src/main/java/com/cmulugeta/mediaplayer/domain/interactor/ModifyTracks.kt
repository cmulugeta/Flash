package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.RequestError

class ModifyTracks(
    private val repository: Repository,
    private val scheduler: BaseScheduler,
    private val errorHandler: ErrorHandler
) : ModifyInteractor {

  override fun insert(success: () -> Unit, error: (RequestError) -> Unit, request: ModifyRequest) {
    repository.insert(request)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, { throwable ->
          error(errorHandler.handle(throwable))
        })
  }

  override fun clearAll(success: () -> Unit, error: (RequestError) -> Unit, type: TrackType) {
    repository.clearAll(type)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, { throwable ->
          error(errorHandler.handle(throwable))
        })
  }

  override fun remove(success: () -> Unit, error: (RequestError) -> Unit, request: ModifyRequest) {
    repository.remove(request)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, { throwable ->
          error(errorHandler.handle(throwable))
        })
  }
}
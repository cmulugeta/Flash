package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.interactor.params.Consumer
import com.cmulugeta.mediaplayer.domain.interactor.params.Response
import io.reactivex.Single

abstract class SingleInteractor<Params>(val repository: Repository, val scheduler: BaseScheduler) {
  fun execute(consumer: Consumer<Params>, params: Params? = null) {
    buildCase(params).subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(consumer.success, consumer.error)
  }

  protected abstract fun buildCase(params: Params? = null): Single<Response<Params>>
}
package com.cmulugeta.mediaplayer.domain.usecases

import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.IRepository
import com.cmulugeta.mediaplayer.domain.usecases.params.Consumer
import com.cmulugeta.mediaplayer.domain.usecases.params.Response
import io.reactivex.Single

abstract class SingleInteractor<Params> (val repository: IRepository, val scheduler: BaseScheduler){
    fun execute(consumer: Consumer<Params>, params:Params?=null){
        buildCase(params).subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }

    protected abstract fun buildCase(params:Params?=null): Single<Response<Params>>
}
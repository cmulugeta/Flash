package com.cmulugeta.mediaplayer.domain.usecases

import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.IRepository
import com.cmulugeta.mediaplayer.domain.usecases.params.SimpleConsumer
import io.reactivex.Completable

abstract class CompletableInteractor<in Params> (val repository: IRepository, val scheduler: BaseScheduler){
    fun execute(consumer:SimpleConsumer,params:Params?=null){
        buildCase(params).observeOn(scheduler.io())
                .subscribeOn(scheduler.ui())
                .subscribe(consumer.onSuccess,consumer.onError)
    }

    protected abstract fun buildCase(params:Params?=null):Completable
}
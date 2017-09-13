package com.cmulugeta.mediaplayer.domain.interactor

import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.disposables.CompositeDisposable
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler

abstract class SingleInteractor<T, in Params> constructor(val scheduler: BaseScheduler){

    private val disposables=CompositeDisposable()

    open fun execute(success: Consumer<in T>, error:Consumer<in Throwable>,params: Params?=null){
        disposables.add(buildObservable(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error))
    }

    fun dispose(){
        disposables.clear()
    }

    protected abstract fun buildObservable(params:Params?=null):Single<T>
}
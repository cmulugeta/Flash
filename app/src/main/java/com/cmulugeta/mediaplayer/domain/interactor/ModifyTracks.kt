package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam
import com.cmulugeta.mediaplayer.domain.interactor.params.SimpleConsumer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModifyTracks @Inject
constructor(val repository: Repository, val scheduler: BaseScheduler):ModifyInteractor{

    override fun insert(success:()->Unit,error:(Throwable)->Unit, param: ModifyParam) {
        repository.insert(param)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    override fun clearAll(success:()->Unit,error:(Throwable)->Unit, type: TrackType) {
        repository.clearAll(type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    override fun remove(success:()->Unit,error:(Throwable)->Unit, param: ModifyParam) {
        repository.remove(param)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }
}
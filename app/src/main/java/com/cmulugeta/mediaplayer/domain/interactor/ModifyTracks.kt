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

    override fun insert(consumer: SimpleConsumer, param: ModifyParam) {
        repository.insert(param)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.onSuccess,consumer.onError)
    }

    override fun clearAll(consumer: SimpleConsumer, type: TrackType) {
        repository.clearAll(type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.onSuccess,consumer.onError)
    }

    override fun remove(consumer: SimpleConsumer, param: ModifyParam) {
        repository.remove(param)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.onSuccess,consumer.onError)
    }
}
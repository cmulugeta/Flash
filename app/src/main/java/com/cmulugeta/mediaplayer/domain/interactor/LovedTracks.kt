package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class LovedTracks @Inject
constructor(private val repository: Repository, scheduler: BaseScheduler):
        SingleInteractor<List<Track>,Void>(scheduler),
        ClearInteractor<Track>, InsertInteractor<Track>{

    override fun insert(success: () -> Unit, error: (Throwable) -> Unit, params: Track) {
        repository.like(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success,error)
    }

    override fun clear(complete: () -> Unit, error: (Throwable) -> Unit) {
        repository.clearLoved()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(complete,error)
    }

    override fun remove(complete: () -> Unit, error: (Throwable) -> Unit, params: Track) {
        repository.removeLoved(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(complete,error)
    }

    override fun buildObservable(params: Void?)=repository.fetchLiked()
}
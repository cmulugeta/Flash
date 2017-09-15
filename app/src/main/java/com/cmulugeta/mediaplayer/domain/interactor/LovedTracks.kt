package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LovedTracks @Inject constructor(val repository: Repository,
                                               scheduler: BaseScheduler):
        SingleInteractor<List<Track>,Void>(scheduler){

    override fun buildObservable(params: Void?): Single<List<Track>> {
        return repository.fetchLiked()
    }
}
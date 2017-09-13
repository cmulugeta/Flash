package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import io.reactivex.Single

class TrackHistory constructor(val repository: Repository,
                               scheduler: BaseScheduler) :
        SingleInteractor<List<Track>,Void?>(scheduler){

    public override fun buildObservable(params: Void?): Single<List<Track>> {
        return repository.fetchHistory()
    }
}

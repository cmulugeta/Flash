package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import java.lang.IllegalArgumentException
import io.reactivex.Single

class SearchTracks constructor(val repository: Repository,
                               scheduler: BaseScheduler):
        SingleInteractor<List<Track>,String>(scheduler){

    override fun buildObservable(params: String?): Single<List<Track>> {
        if(!params.isNullOrBlank()){
            return repository.query(params)
        }
        return Single.error(IllegalArgumentException("Query is null or empty!"))
    }
}
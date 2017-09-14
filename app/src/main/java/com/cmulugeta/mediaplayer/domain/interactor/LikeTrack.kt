package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LikeTrack @Inject constructor(val repository: Repository,
                            scheduler: BaseScheduler):
        CompletableInteractor<Track>(scheduler){

    override fun buildCompletable(params: Track?): Completable {
        if(params!=null){
            return repository.like(params)
        }
        return Completable.error(IllegalArgumentException("Track is null"))
    }
}

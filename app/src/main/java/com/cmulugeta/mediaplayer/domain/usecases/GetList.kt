package com.cmulugeta.mediaplayer.domain.usecases

import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.IRepository
import com.cmulugeta.mediaplayer.ifNotNull
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetList @Inject constructor(repository: IRepository, scheduler: BaseScheduler)
    :SingleInteractor<TrackType>(repository,scheduler){

    override fun buildCase(params: TrackType?)
            =params.ifNotNull(repository::fetch,
            Single.error(IllegalArgumentException()))
}
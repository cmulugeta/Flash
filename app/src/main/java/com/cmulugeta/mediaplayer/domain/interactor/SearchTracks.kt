package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.ifNotNull
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchTracks @Inject constructor(repository: Repository, scheduler: BaseScheduler)
    :SingleInteractor<SearchPage>(repository,scheduler){

    override fun buildCase(params: SearchPage?)
            =params.ifNotNull(repository::search,
            Single.error(IllegalArgumentException()))
}
package com.cmulugeta.mediaplayer.domain

import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam
import com.cmulugeta.mediaplayer.domain.interactor.params.Response
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
    fun clearAll(type:TrackType):Completable
    fun remove(param:ModifyParam):Completable
    fun insert(param: ModifyParam):Completable
    fun fetch(type:TrackType): Single<Response<TrackType>>
    fun search(page: SearchPage):Single<Response<SearchPage>>
}
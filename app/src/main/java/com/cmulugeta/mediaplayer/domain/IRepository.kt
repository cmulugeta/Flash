package com.cmulugeta.mediaplayer.domain

import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.usecases.params.Response
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository{
    fun clear(type:TrackType):Completable
    fun remove(track:Track,type:TrackType):Completable
    fun insert(track:Track,type: TrackType):Completable
    fun fetch(type:TrackType): Single<Response<TrackType>>
    fun search(page: SearchPage):Single<Response<SearchPage>>
}
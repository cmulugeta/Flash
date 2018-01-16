package com.cmulugeta.mediaplayer.domain

import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.Track
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
  fun clearAll(type: TrackType): Completable
  fun remove(request: ModifyRequest): Completable
  fun insert(request: ModifyRequest): Completable
  fun fetch(type: TrackType): Single<List<Track>>
  fun search(page: SearchPage): Single<List<Track>>
}
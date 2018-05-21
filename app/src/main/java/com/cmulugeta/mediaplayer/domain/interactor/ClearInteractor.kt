package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.RequestError
import com.cmulugeta.mediaplayer.domain.model.TrackType

interface ClearInteractor {
  fun clearAll(success: () -> Unit, error: (RequestError) -> Unit, type: TrackType)
  fun remove(success: () -> Unit, error: (RequestError) -> Unit, request: ModifyRequest)
}
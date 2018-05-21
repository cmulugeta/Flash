package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.RequestError

interface InsertInteractor {
  fun insert(success: () -> Unit, error: (RequestError) -> Unit, request: ModifyRequest)
}
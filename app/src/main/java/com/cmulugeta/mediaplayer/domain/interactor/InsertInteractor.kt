package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest

interface InsertInteractor {
  fun insert(success: () -> Unit, error: (Throwable) -> Unit, request: ModifyRequest)
}
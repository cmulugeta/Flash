package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.model.Connection
import com.cmulugeta.mediaplayer.domain.model.RequestError
import com.cmulugeta.mediaplayer.domain.model.FailedRequest
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandler {
  fun handle(throwable: Throwable): RequestError {
    return when(throwable) {
      is UnknownHostException-> Connection
      is SocketTimeoutException-> Connection
      else -> FailedRequest
    }
  }
}
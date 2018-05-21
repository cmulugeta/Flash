package com.cmulugeta.mediaplayer.domain.model

sealed class RequestError
object Connection : RequestError()
object FailedRequest : RequestError()
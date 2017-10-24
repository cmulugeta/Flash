package com.cmulugeta.mediaplayer.domain.usecases.params

class Consumer<in Request>(val success:(Response<Request>)->Unit, val error:(Throwable)->Unit)
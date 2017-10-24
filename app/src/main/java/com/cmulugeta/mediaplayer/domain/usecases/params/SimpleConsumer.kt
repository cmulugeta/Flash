package com.cmulugeta.mediaplayer.domain.usecases.params

class SimpleConsumer(val onSuccess:()->Unit, val onError:(Throwable)->Unit)
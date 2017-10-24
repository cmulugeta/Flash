package com.cmulugeta.mediaplayer.domain.interactor.params

class SimpleConsumer(val onSuccess:()->Unit, val onError:(Throwable)->Unit)
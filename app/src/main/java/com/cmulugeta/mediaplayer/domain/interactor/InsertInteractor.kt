package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam
import com.cmulugeta.mediaplayer.domain.interactor.params.SimpleConsumer

interface InsertInteractor{
    fun insert(success:()->Unit,error:(Throwable)->Unit,param: ModifyParam)
}
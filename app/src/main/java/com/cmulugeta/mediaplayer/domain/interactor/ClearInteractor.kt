package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam

interface ClearInteractor{
    fun clearAll(success:()->Unit,error:(Throwable)->Unit,type:TrackType)
    fun remove(success:()->Unit,error:(Throwable)->Unit,param: ModifyParam)
}
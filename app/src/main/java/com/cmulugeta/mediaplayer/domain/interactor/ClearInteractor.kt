package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam
import com.cmulugeta.mediaplayer.domain.interactor.params.SimpleConsumer

interface ClearInteractor{
    fun clearAll(consumer: SimpleConsumer,type:TrackType)
    fun remove(consumer: SimpleConsumer,param: ModifyParam)
}
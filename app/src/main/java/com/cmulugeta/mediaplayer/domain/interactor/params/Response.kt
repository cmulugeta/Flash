package com.cmulugeta.mediaplayer.domain.interactor.params

import com.cmulugeta.mediaplayer.domain.model.Track

class Response<out Request>(val request:Request,val result:List<Track>)
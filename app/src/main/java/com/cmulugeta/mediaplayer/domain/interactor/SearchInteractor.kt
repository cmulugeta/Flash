package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.model.Track

interface SearchInteractor {
    fun query(success:(List<Track>)->Unit,error:(Throwable)->Unit,query:String?)
    fun nextPage(success: (List<Track>) -> Unit,error: (Throwable) -> Unit)
    fun dispose()
}
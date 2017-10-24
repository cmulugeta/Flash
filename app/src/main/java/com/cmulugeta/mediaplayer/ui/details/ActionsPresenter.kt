package com.cmulugeta.mediaplayer.ui.details

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.View
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.Presenter
import com.cmulugeta.mediaplayer.domain.interactor.ModifyInteractor
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyParam
import com.cmulugeta.mediaplayer.domain.model.TrackType
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
class ActionsPresenter @Inject
constructor(val modifier:ModifyInteractor): Presenter{

    private lateinit var view:View

    override fun add(track: Track){
        val param=ModifyParam(track,TrackType.HISTORY)
        modifier.insert(view::added,this::error,param)
    }

    override fun remove(track: Track){
        val param=ModifyParam(track,TrackType.HISTORY)
        modifier.remove(view::removed,this::error,param)
    }

    override fun dislike(track: Track){
        val param=ModifyParam(track,TrackType.FAVORITE)
        modifier.remove(view::disliked,this::error,param)
    }

    override fun like(track: Track){
        val param=ModifyParam(track,TrackType.FAVORITE)
        modifier.insert(view::liked,this::error,param)
    }

    override fun stop(){}

    private fun error(ex:Throwable){
        view.error()
        ex.printStackTrace()
    }

    override fun attach(view: View) {
        this.view=view
    }
}
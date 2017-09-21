package com.cmulugeta.mediaplayer.ui.details

import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.ClearInteractor
import com.cmulugeta.mediaplayer.domain.interactor.InsertInteractor
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.View
import javax.inject.Inject
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.Presenter

@ViewScope
class ActionsPresenter @Inject constructor(private val liker:InsertInteractor<Track>,
                                           private val adder:InsertInteractor<Track>,
                                           private val disliker:ClearInteractor<Track>,
                                           private val remover:ClearInteractor<Track>): Presenter{
    private lateinit var view:View

    override fun add(track: Track)=adder.insert({view.added()},this::error,track)
    override fun remove(track: Track)=remover.remove({view.removed()},this::error,track)
    override fun dislike(track: Track)=disliker.remove({view.disliked()},this::error,track)
    override fun like(track: Track)=liker.insert({view.liked()},this::error,track)

    override fun stop(){}

    private fun error(ex:Throwable){
        view.error()
        ex.printStackTrace()
    }

    override fun attach(view: View) {
        this.view=view
    }
}
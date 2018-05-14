package com.cmulugeta.mediaplayer.ui.details

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.Presenter
import com.cmulugeta.mediaplayer.domain.interactor.ModifyInteractor
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.TrackType

class ActionsPresenter (
    private val modifier: ModifyInteractor,
    private val view : ActionsContract.View
) : Presenter {

  override fun add(track: Track) {
    val param = ModifyRequest(TrackType.History, track)
    modifier.insert({ view.showAdded(TrackType.History) }, this::error, param)
  }

  override fun remove(track: Track) {
    val param = ModifyRequest(TrackType.History, track)
    modifier.remove({ view.showRemoved(TrackType.History) }, this::error, param)
  }

  override fun dislike(track: Track) {
    val param = ModifyRequest(TrackType.Favorite, track)
    modifier.remove({ view.showRemoved(TrackType.Favorite) }, this::error, param)
  }

  override fun like(track: Track) {
    val param = ModifyRequest(TrackType.Favorite, track)
    modifier.insert({ view.showAdded(TrackType.Favorite) }, this::error, param)
  }

  private fun error(ex: Throwable) {
    view.error()
    ex.printStackTrace()
  }
}
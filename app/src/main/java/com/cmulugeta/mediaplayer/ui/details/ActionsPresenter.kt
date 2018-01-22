package com.cmulugeta.mediaplayer.ui.details

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.View
import com.cmulugeta.mediaplayer.ui.details.ActionsContract.Presenter
import com.cmulugeta.mediaplayer.domain.interactor.ModifyInteractor
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.TrackType
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
class ActionsPresenter @Inject constructor(private val modifier: ModifyInteractor) : Presenter {

  private lateinit var view: View

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

  override fun attach(view: View) {
    this.view = view
  }
}
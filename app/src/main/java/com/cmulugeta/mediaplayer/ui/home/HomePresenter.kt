package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.home.HomeContract.*
import com.cmulugeta.mediaplayer.domain.interactor.ClearInteractor
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.interactor.params.ModifyRequest
import com.cmulugeta.mediaplayer.domain.model.TrackType

@ViewScope
abstract class HomePresenter constructor(val interactor: SingleInteractor<TrackType, List<Track>>,
                                         val clear: ClearInteractor) : Presenter {

  protected lateinit var view: View

  override fun start() {
    view.setLoading(true)
    interactor.execute(this::onSuccess, this::onError, type())
  }

  override fun refresh() {
    start()
  }

  private fun onSuccess(response: List<Track>) {
    view.show(response)
    view.setLoading(false)
  }

  private fun onError(error: Throwable) {
    error.printStackTrace()
    view.setLoading(false)
    view.error()
  }

  override fun attach(view: View) {
    this.view = view
  }

  override fun remove(track: Track) {
    clear.remove({ view.removed(track) }, this::onError, ModifyRequest(type(), track))
  }

  override fun clear() {
    clear.clearAll(view::cleared, this::onError, type())
  }

  protected abstract fun type(): TrackType
}

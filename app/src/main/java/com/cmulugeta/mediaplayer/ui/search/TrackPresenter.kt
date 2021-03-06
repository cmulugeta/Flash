package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.model.Connection
import com.cmulugeta.mediaplayer.domain.model.RequestError
import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.Track

class TrackPresenter (
    private val interactor: SingleInteractor<SearchPage, List<Track>>,
    private val view : SearchContract.View<Track>
) : SearchContract.Presenter<Track> {

  private val page = SearchPage(0)

  override fun query(query: String?) {
    page.invalidate()
    page.query = query
    executeQuery()
  }

  override fun refresh() {
    page.invalidate()
    interactor.execute(this::onSuccess, this::onError, page)
  }

  private fun executeQuery() {
    view.showLoading()
    interactor.execute(this::onSuccess, this::onError, page)
  }

  private fun onSuccess(page: SearchPage, result: List<Track>) {
    view.hideLoading()
    view.hideRefreshing()
    if (page.isFirst)
      view.showResult(result)
    else
      view.appendResult(result)
  }

  private fun onError(error: RequestError) {
    when(error) {
      is Connection-> view.onConnectionError()
    }
    view.hideLoading()
  }

  override fun more() {
    page.next()
    executeQuery()
  }
}

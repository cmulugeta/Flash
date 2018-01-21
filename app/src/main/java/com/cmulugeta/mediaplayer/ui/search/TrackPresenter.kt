package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.kotlin_extensions.error
import com.cmulugeta.kotlin_extensions.info
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.domain.model.Track
import javax.inject.Inject

@ViewScope
class TrackPresenter @Inject constructor(val interactor: SingleInteractor<SearchPage, List<Track>>)
  : SearchContract.Presenter<Track> {

  private val page = SearchPage(0)
  private lateinit var view: SearchContract.View<Track>

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
    info(page.current)
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

  private fun onError(throwable: Throwable) {
    error(throwable.message)
    view.hideLoading()
  }

  override fun more() {
    page.next()
    executeQuery()
  }
  override fun attachView(view: SearchContract.View<Track>) {
    this.view = view
  }
}

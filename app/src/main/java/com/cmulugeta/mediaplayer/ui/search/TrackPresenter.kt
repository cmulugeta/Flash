package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.kotlin_extensions.error
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
    page.query = query
    executeQuery()
  }

  override fun refresh() {
    page.invalidate()
    executeQuery(loading = false)
  }

  private fun executeQuery(loading:Boolean = true) {
    if(loading) view.showLoading()
    interactor.execute(this::onSuccess, this::onError, page)
  }

  private fun onSuccess(page: SearchPage, result: List<Track>) {
    view.hideLoading()
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

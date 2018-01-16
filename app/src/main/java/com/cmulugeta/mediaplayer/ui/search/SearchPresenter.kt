package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.ui.search.SearchContract.*
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.interactor.params.Consumer
import com.cmulugeta.mediaplayer.domain.interactor.params.Response
import com.cmulugeta.mediaplayer.domain.model.SearchPage
import com.cmulugeta.mediaplayer.then
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
class SearchPresenter @Inject constructor(val search: SingleInteractor<SearchPage>) : Presenter {

  private lateinit var view: View
  private var page = SearchPage(1)

  override fun query(query: String?) {
    view.setLoading(true)
    page.query = query
    search.execute(Consumer(this::onSuccess, this::onError), page)
  }

  override fun more() {
    view.setLoading(true)
    page.current++
    search.execute(Consumer(this::append, this::onError), page)
  }

  override fun attachView(view: View) {
    this.view = view
  }

  private fun onSuccess(response: Response<SearchPage>) {
    view.setLoading(false)
    response.result.isEmpty().then(view::empty, { view.show(response.result) })
  }

  private fun append(response: Response<SearchPage>) {
    view.setLoading(false)
    val list = response.result
    view.append(list)
  }

  private fun onError(error: Throwable) {
    error.printStackTrace()
    view.setLoading(false)
    view.error()
  }

  override fun stop() {}
}
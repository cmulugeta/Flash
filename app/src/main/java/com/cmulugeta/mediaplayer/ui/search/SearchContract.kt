package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface SearchContract {
  interface View : BaseView<Presenter> {
    fun setLoading(isLoading: Boolean)
    fun show(list: List<Track>)
    fun append(list: List<Track>)
    fun error()
    fun empty()
  }

  interface Presenter : BasePresenter {
    fun query(query: String?)
    fun more()
    fun attachView(view: View)
  }
}
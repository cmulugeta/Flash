package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface HomeContract {
  interface View : BaseView<Presenter> {
    fun show(list: List<Track>)
    fun error()
    fun empty()
    fun cleared()
    fun removed(track: Track)
    fun setLoading(isLoading: Boolean)
  }

  interface Presenter : BasePresenter {
    fun start()
    fun remove(track: Track)
    fun clear()
    fun attach(view: View)
    fun refresh()
  }
}

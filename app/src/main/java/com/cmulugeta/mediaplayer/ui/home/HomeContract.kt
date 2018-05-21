package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface HomeContract {
  interface View : BaseView<Presenter> {
    fun showTracks(list: List<Track>)
    fun error()
    fun showEmpty()
    fun showCleared()
    fun removed(track: Track)
    fun showLoading()
    fun hideLoading()
  }

  interface Presenter : BasePresenter {
    fun start()
    fun remove(track: Track)
    fun clearAll()
    fun refresh()
  }
}

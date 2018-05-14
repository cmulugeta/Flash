package com.cmulugeta.mediaplayer.ui.search

import android.support.annotation.StringRes
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface SearchContract {
  interface View<T> : BaseView<Presenter<T>> {
    fun showLoading()
    fun hideLoading()
    fun showRefreshing()
    fun hideRefreshing()
    fun showResult(list: List<T>)
    fun appendResult(list: List<T>)
    fun showMessage(@StringRes id: Int)
    fun error()
    fun empty()
  }

  interface Presenter<T> : BasePresenter {
    fun query(query: String?)
    fun more()
    fun refresh()
  }
}
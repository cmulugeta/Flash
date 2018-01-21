package com.cmulugeta.mediaplayer.ui.search

import android.os.Bundle
import android.support.annotation.TransitionRes
import android.support.v4.app.Fragment
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmulugeta.kotlin_extensions.hide
import com.cmulugeta.kotlin_extensions.show
import com.cmulugeta.kotlin_extensions.then
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.addReachBottomListener
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.utils.showMessage
import kotlinx.android.synthetic.main.fragment_search.*

abstract class SearchFragment<T> : Fragment(), SearchContract.View<T>, QueryCallback {
  abstract val adapter: BaseAdapter<T>
  abstract var presenter: SearchContract.Presenter<T>?

  abstract fun inject()

  override fun showResult(list: List<T>) {
    empty.hide(isGone = true)
    result.show()
    adapter.data = list.toMutableList()
  }

  override fun appendResult(list: List<T>) {
    empty.hide(isGone = true)
    adapter.appendData(list)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_search, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    inject()
    refresher.setOnRefreshListener {
      presenter?.refresh()
    }
    result.adapter = adapter
    result.addReachBottomListener(refresher,{
      presenter?.more()
    })
  }

  override fun inputCleared() {
    adapter.clear()
    refreshPage(false )
  }

  override fun queryTyped(query: String?) {
    presenter?.query(query)
  }

  override fun showLoading() {
    progress.show()
  }

  override fun hideLoading() {
    progress.hide(isGone = true)
  }

  override fun showRefreshing() {
    refresher.isRefreshing = true
  }

  override fun hideRefreshing() {
    refresher.isRefreshing = false
  }

  override fun empty() {
    empty.show()
  }

  override fun error() {
    root.showMessage(R.string.error)
  }

  override fun showMessage(id: Int) {
    root.showMessage(id)
  }

  private fun refreshPage(visible: Boolean) {
    val transition = getTransition(visible then R.transition.search_show ?: R.transition.search_show)
    TransitionManager.beginDelayedTransition(root, transition)
    result.visibility = visible then View.VISIBLE ?: View.GONE
  }

  private fun getTransition(@TransitionRes transitionId: Int): Transition {
    val inflater = TransitionInflater.from(context)
    return inflater.inflateTransition(transitionId)
  }
}
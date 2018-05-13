package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment

class HistoryFragment : HomeFragment() {
  override var presenter: Presenter? = null
  override fun emptyMessage(): Int = R.string.empty_history
  override fun alertMessage(): String = getString(R.string.history_alert)
}

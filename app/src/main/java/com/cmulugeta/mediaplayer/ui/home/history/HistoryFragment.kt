package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.Params
import com.cmulugeta.mediaplayer.injectWith
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment
import org.koin.android.ext.android.inject

class HistoryFragment : HomeFragment() {
  override val presenter: Presenter by injectWith (Params.HISTORY)
  override fun alertMessage(): String = getString(R.string.history_alert)
}

package com.cmulugeta.mediaplayer.ui.home.favorite

import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment

class FavoriteFragment : HomeFragment() {
  override var presenter: Presenter? = null

  override fun emptyMessage(): Int = R.string.empty_liked
  override fun alertMessage(): String = getString(R.string.loved_alert)
}

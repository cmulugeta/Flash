package com.cmulugeta.mediaplayer.ui.home.favorite

import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.Params
import com.cmulugeta.mediaplayer.injectWith
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment

class FavoriteFragment : HomeFragment() {
  override val presenter: Presenter by injectWith(Params.FAVORITE)
  override fun alertMessage(): String = getString(R.string.loved_alert)
}

package com.cmulugeta.mediaplayer.ui.home.favorite

import com.cmulugeta.mediaplayer.App
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment
import javax.inject.Inject

class LovedFragment : HomeFragment() {
  override var presenter: Presenter? = null
    @Inject set(@Loved value) {
      field = value
      field?.attach(this)
    }

  override fun inject() {
    DaggerViewComponent.builder()
        .applicationComponent(App.component)
        .presenterModule(PresenterModule())
        .build().inject(this)
  }

  override fun emptyMessage(): Int = R.string.empty_liked
  override fun alertMessage(): String = getString(R.string.loved_alert)

  override fun attach(presenter: Presenter) {
  }
}

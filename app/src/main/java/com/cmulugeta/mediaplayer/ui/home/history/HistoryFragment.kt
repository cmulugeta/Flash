package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.App
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.di.qualifier.History
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment
import javax.inject.Inject

class HistoryFragment : HomeFragment() {
  override var presenter: Presenter? = null
    @Inject set(@History value) {
      field = value
      field?.attach(this)
    }

  override fun inject() {
    DaggerViewComponent.builder()
        .applicationComponent(App.component)
        .presenterModule(PresenterModule())
        .build().inject(this)
  }

  override fun attach(presenter: Presenter) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  override fun emptyMessage(): Int = R.string.empty_history
  override fun alertMessage(): String = getString(R.string.history_alert)
}

package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.App
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.base.Navigator
import com.cmulugeta.mediaplayer.ui.home.TrackAdapter
import javax.inject.Inject

class TrackFragment : SearchFragment<Track>() {
  @Inject lateinit var navigator: Navigator

  override val adapter: BaseAdapter<Track> by lazy(LazyThreadSafetyMode.NONE) {
    TrackAdapter(context, { navigator.navigate(activity, it) }, { navigator.actions(activity, it) })
  }

  override var presenter: SearchContract.Presenter<Track>? = null
    @Inject set(value) {
      field = value
      field?.attachView(this)
    }

  override fun inject() {
    DaggerViewComponent.builder()
        .applicationComponent(App.component)
        .presenterModule(PresenterModule())
        .build().inject(this)
  }
}
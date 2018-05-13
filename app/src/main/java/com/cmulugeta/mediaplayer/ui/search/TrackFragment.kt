package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.base.Navigator
import com.cmulugeta.mediaplayer.ui.home.TrackAdapter
import org.koin.android.ext.android.inject

class TrackFragment : SearchFragment<Track>() {
  override var presenter: SearchContract.Presenter<Track>? = null

  private val navigator: Navigator by inject()

  override val adapter: BaseAdapter<Track> by lazy(LazyThreadSafetyMode.NONE) {
    TrackAdapter(context, { navigator.navigate(activity, it) }, { navigator.actions(activity, it) })
  }
}
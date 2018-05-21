package com.cmulugeta.mediaplayer.ui.search

import com.cmulugeta.mediaplayer.di.Params
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.injectWith
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.home.TrackAdapter

class TrackFragment : SearchFragment<Track>() {
  override val presenter: SearchContract.Presenter<Track> by injectWith(Params.SEARCH)
  override val adapter: BaseAdapter<Track> by lazy {
    TrackAdapter(context!!, { navigator.navigate(activity!!, it) },
        { navigator.actions(activity!!, it) })
  }
}
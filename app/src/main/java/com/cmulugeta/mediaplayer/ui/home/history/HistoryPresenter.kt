package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.ui.home.HomePresenter
import com.cmulugeta.mediaplayer.domain.interactor.ClearInteractor
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.ui.home.HomeContract

class HistoryPresenter(
    interactor: SingleInteractor<TrackType, List<Track>>,
    clear: ClearInteractor,
    view: HomeContract.View
) : HomePresenter(interactor, clear, view) {
  override val trackType: TrackType
    get() = TrackType.History
}
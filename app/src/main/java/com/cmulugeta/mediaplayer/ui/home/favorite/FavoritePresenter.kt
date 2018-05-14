package com.cmulugeta.mediaplayer.ui.home.favorite

import com.cmulugeta.mediaplayer.ui.home.HomePresenter
import com.cmulugeta.mediaplayer.domain.interactor.ClearInteractor
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.ui.home.HomeContract

class FavoritePresenter(
    interactor: SingleInteractor<TrackType, List<Track>>,
    clear: ClearInteractor,
    view: HomeContract.View
) : HomePresenter(interactor, clear, view) {
  override fun type() = TrackType.Favorite
}

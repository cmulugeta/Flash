package com.cmulugeta.mediaplayer.ui.home.favorite

import com.cmulugeta.mediaplayer.ui.home.HomePresenter
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.ClearInteractor
import com.cmulugeta.mediaplayer.domain.interactor.SingleInteractor
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.model.TrackType

@ViewScope
class LovedPresenter @Inject constructor(interactor: SingleInteractor<TrackType, List<Track>>, clear: ClearInteractor) : HomePresenter(interactor, clear) {
  override fun type() = TrackType.FAVORITE
}

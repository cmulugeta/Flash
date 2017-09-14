package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import com.cmulugeta.mediaplayer.ui.home.HomePresenter
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
class HistoryPresenter @Inject constructor(interactor:TrackHistory): HomePresenter(interactor)

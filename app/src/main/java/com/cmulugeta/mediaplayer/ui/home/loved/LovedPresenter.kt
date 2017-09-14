package com.cmulugeta.mediaplayer.ui.home.loved

import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.ui.home.HomePresenter

@ViewScope
class LovedPresenter @Inject constructor(interactor:LovedTracks): HomePresenter(interactor)

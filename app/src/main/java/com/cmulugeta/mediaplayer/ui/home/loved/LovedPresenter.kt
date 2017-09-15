package com.cmulugeta.mediaplayer.ui.home.loved

import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.ui.home.HomePresenter
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
class LovedPresenter @Inject constructor(interactor:LovedTracks) : HomePresenter(interactor,interactor)

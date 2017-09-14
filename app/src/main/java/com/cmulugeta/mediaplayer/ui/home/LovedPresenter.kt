package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LovedPresenter @Inject constructor(interactor:LovedTracks):HomePresenter(interactor)

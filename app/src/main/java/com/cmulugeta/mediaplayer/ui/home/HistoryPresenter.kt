package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryPresenter @Inject constructor(interactor:TrackHistory):HomePresenter(interactor)

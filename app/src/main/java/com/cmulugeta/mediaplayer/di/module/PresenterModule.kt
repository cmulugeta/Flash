package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.home.loved.LovedPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{

    @ViewScope
    @Provides
    fun history(interactor:TrackHistory)= HistoryPresenter(interactor)

    @ViewScope
    @Provides
    fun loved(interactor:LovedTracks)= LovedPresenter(interactor)
}

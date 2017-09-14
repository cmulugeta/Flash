package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.di.qualifier.History
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.home.loved.LovedPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{

    @ViewScope
    @Provides
    @History
    fun history(interactor:TrackHistory):HomeContract.Presenter= HistoryPresenter(interactor)

    @ViewScope
    @Provides
    @Loved
    fun loved(interactor:LovedTracks):HomeContract.Presenter= LovedPresenter(interactor)
}

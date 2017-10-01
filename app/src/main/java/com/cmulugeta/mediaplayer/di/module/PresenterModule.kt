package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.domain.interactor.LovedTracks
import com.cmulugeta.mediaplayer.domain.interactor.TrackHistory
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.home.loved.LovedPresenter
import com.cmulugeta.mediaplayer.di.qualifier.History
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.interactor.SearchInteractor
import com.cmulugeta.mediaplayer.domain.interactor.SearchTracks
import com.cmulugeta.mediaplayer.ui.details.ActionsContract
import com.cmulugeta.mediaplayer.ui.details.ActionsPresenter
import com.cmulugeta.mediaplayer.ui.search.SearchContract
import com.cmulugeta.mediaplayer.ui.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{
    @ViewScope
    @History
    @Provides
    fun history(interactor:TrackHistory):HomeContract.Presenter= HistoryPresenter(interactor)

    @ViewScope
    @Loved
    @Provides
    fun loved(interactor:LovedTracks):HomeContract.Presenter= LovedPresenter(interactor)

    @ViewScope
    @Provides
    fun search(interactor:SearchTracks):SearchContract.Presenter= SearchPresenter(interactor)

    @ViewScope
    @Provides
    fun actions(lover:LovedTracks,history: TrackHistory):ActionsContract.Presenter=ActionsPresenter(lover,history,lover,history)
}

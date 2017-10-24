package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.home.loved.LovedPresenter
import com.cmulugeta.mediaplayer.domain.interactor.*
import com.cmulugeta.mediaplayer.ui.details.ActionsContract
import com.cmulugeta.mediaplayer.ui.details.ActionsPresenter
import com.cmulugeta.mediaplayer.ui.search.SearchContract
import com.cmulugeta.mediaplayer.ui.search.SearchPresenter
import com.cmulugeta.mediaplayer.di.qualifier.History
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{
    @ViewScope
    @History
    @Provides
    fun history(tracks:GetTracks,modify: ModifyTracks)
            :HomeContract.Presenter
            =HistoryPresenter(tracks,modify)

    @ViewScope
    @Loved
    @Provides
    fun loved(tracks:GetTracks,modify: ModifyTracks)
            :HomeContract.Presenter
            =LovedPresenter(tracks,modify)

    @ViewScope
    @Provides
    fun search(search:SearchTracks)
            :SearchContract.Presenter
            =SearchPresenter(search)

    @ViewScope
    @Provides
    fun actions(modify: ModifyTracks)
            :ActionsContract.Presenter
            =ActionsPresenter(modify)
}

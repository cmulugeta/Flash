package com.cmulugeta.mediaplayer.di.module

import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.history.HistoryPresenter
import com.cmulugeta.mediaplayer.ui.home.favorite.LovedPresenter
import com.cmulugeta.mediaplayer.domain.interactor.*
import com.cmulugeta.mediaplayer.ui.details.ActionsContract
import com.cmulugeta.mediaplayer.ui.details.ActionsPresenter
import com.cmulugeta.mediaplayer.ui.search.SearchContract
import com.cmulugeta.mediaplayer.di.qualifier.History
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.di.scope.ViewScope
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.search.TrackPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
  @ViewScope
  @History
  @Provides
  internal fun history(tracks: GetTracks, modify: ModifyTracks)
      : HomeContract.Presenter
      = HistoryPresenter(tracks, modify)

  @ViewScope
  @Loved
  @Provides
  internal fun loved(tracks: GetTracks, modify: ModifyTracks)
      : HomeContract.Presenter
      = LovedPresenter(tracks, modify)

  @ViewScope
  @Provides
  internal fun search(interactor: SearchTracks)
      : SearchContract.Presenter<Track>
      = TrackPresenter(interactor)

  @ViewScope
  @Provides
  internal fun actions(modify: ModifyTracks)
      : ActionsContract.Presenter
      = ActionsPresenter(modify)
}

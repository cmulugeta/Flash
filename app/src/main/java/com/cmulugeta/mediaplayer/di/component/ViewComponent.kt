package com.cmulugeta.mediaplayer.di.component

import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.ui.home.history.HistoryFragment
import com.cmulugeta.mediaplayer.ui.home.favorite.LovedFragment
import com.cmulugeta.mediaplayer.ui.search.SearchActivity
import com.cmulugeta.mediaplayer.ui.details.ActionsActivity
import com.cmulugeta.mediaplayer.ui.search.TrackFragment
import dagger.Component
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
@Component(dependencies = [(ApplicationComponent::class)],
    modules = [(PresenterModule::class)])
interface ViewComponent {
  fun inject(fragment: LovedFragment)
  fun inject(fragment: HistoryFragment)
  fun inject(fragment: TrackFragment)
  fun inject(activity: SearchActivity)
  fun inject(activity: ActionsActivity)
}
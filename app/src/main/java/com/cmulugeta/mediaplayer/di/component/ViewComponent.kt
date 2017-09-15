package com.cmulugeta.mediaplayer.di.component

import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.ui.home.history.HistoryFragment
import com.cmulugeta.mediaplayer.ui.home.loved.LovedFragment
import dagger.Component
import com.cmulugeta.mediaplayer.di.scope.ViewScope

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(PresenterModule::class))
interface ViewComponent{
    fun inject(fragment:LovedFragment)
    fun inject(fragment:HistoryFragment)
}
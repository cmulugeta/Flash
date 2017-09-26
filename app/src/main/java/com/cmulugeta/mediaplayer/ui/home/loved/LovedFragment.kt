package com.cmulugeta.mediaplayer.ui.home.loved

import com.cmulugeta.mediaplayer.FitnessSound
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment
import javax.inject.Inject
import com.cmulugeta.mediaplayer.di.qualifier.Loved

class LovedFragment : HomeFragment() {
    @Inject
    override fun attach(@Loved presenter: Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }

    override fun empty() {
        super.empty()

    }
    override fun inject() {
        DaggerViewComponent.builder()
                .applicationComponent(FitnessSound.app().component())
                .presenterModule(PresenterModule())
                .build().inject(this)
    }
}

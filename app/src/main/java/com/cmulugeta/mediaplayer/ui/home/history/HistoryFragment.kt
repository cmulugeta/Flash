package com.cmulugeta.mediaplayer.ui.home.history

import com.cmulugeta.mediaplayer.FitnessSound
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.home.HomeFragment
import com.cmulugeta.mediaplayer.di.qualifier.History
import javax.inject.Inject

class HistoryFragment : HomeFragment(){
    @Inject
    override fun attach(@History presenter: Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }

    override fun inject() {
        DaggerViewComponent.builder()
                .applicationComponent(FitnessSound.app().component())
                .presenterModule(PresenterModule())
                .build().inject(this)
    }
}

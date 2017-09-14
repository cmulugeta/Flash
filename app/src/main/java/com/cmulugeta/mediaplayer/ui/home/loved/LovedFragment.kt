package com.cmulugeta.mediaplayer.ui.home.loved

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter

class LovedFragment : BaseFragment(), HomeContract.View {

    private lateinit var presenter:Presenter

    override fun show(list: List<Track>) {

    }

    override fun error() {

    }

    override fun empty() {

    }

    override fun attach(presenter: Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }
}

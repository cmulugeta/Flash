package com.cmulugeta.mediaplayer.ui.home.loved

import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.qualifier.Loved
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import javax.inject.Inject

class LovedFragment : BaseFragment(), HomeContract.View {

    private lateinit var presenter:Presenter

    override fun show(list: List<Track>) {

    }

    override fun error() {

    }

    override fun empty() {

    }

    override fun setLoading(isLoading: Boolean) {

    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    @Inject
    @Loved
    override fun attach(presenter: Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }
}

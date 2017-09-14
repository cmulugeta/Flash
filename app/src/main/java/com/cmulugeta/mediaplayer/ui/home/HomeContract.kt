package com.cmulugeta.mediaplayer.ui.home

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface HomeContract {
    interface View : BaseView<Presenter> {
        fun show(list:List<Track>)
        fun error()
        fun empty()
        fun setLoading(isLoading:Boolean)
    }
    interface Presenter : BasePresenter {
        override fun start()
        override fun stop()
        fun attach(view:View)
    }
}

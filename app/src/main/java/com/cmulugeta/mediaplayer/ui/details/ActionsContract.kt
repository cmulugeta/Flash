package com.cmulugeta.mediaplayer.ui.details

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BasePresenter
import com.cmulugeta.mediaplayer.ui.base.BaseView

interface ActionsContract {
    interface View:BaseView<Presenter> {
        fun added()
        fun liked()
        fun disliked()
        fun removed()
        fun error()
    }
    interface Presenter:BasePresenter{
        fun like(track:Track)
        fun dislike(track:Track)
        fun remove(track:Track)
        fun add(track:Track)
        fun attach(view:View)
    }
}
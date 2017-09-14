package com.cmulugeta.mediaplayer.ui.base

interface BaseView<in T : BasePresenter> {
    fun attach(presenter: T)
}
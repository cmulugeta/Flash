package com.cmulugeta.mediaplayer.ui.base

interface BaseView<out T : BasePresenter> {
  val presenter: T
}
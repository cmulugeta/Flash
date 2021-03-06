package com.cmulugeta.mediaplayer.ui.base

import android.support.v7.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
  protected val navigator: Navigator by inject()
}
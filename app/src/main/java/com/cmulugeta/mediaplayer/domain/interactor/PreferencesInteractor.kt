package com.cmulugeta.mediaplayer.domain.interactor

import com.cmulugeta.mediaplayer.domain.model.ImageQuality

interface PreferencesInteractor {
  fun saveImageQuality(quality: ImageQuality)
  fun getImageQuality(): ImageQuality
}
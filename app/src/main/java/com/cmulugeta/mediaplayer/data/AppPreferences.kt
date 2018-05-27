package com.cmulugeta.mediaplayer.data

import com.cmulugeta.mediaplayer.domain.interactor.PreferencesInteractor
import com.cmulugeta.mediaplayer.domain.model.Average
import com.cmulugeta.mediaplayer.domain.model.ImageQuality

class AppPreferences : PreferencesInteractor {
  override fun saveImageQuality(quality: ImageQuality) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getImageQuality(): ImageQuality {
    //TODO needs implementation
    return Average
  }
}
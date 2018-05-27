package com.cmulugeta.mediaplayer.ui.settings

import com.cmulugeta.mediaplayer.domain.interactor.ModifyInteractor
import com.cmulugeta.mediaplayer.domain.interactor.PreferencesInteractor
import com.cmulugeta.mediaplayer.domain.model.ImageQuality
import com.cmulugeta.mediaplayer.domain.model.RequestError
import com.cmulugeta.mediaplayer.domain.model.TrackType

class SettingsPresenter(
    private val preferencesInteractor: PreferencesInteractor,
    private val modifyInteractor: ModifyInteractor,
    private val view: SettingsContract.View
) : SettingsContract.Presenter {
  override fun clearData() {
    modifyInteractor.clearAll(view::showDataCleared, this::handleError, TrackType.All)
  }

  private fun handleError(error: RequestError) {
    view.showError()
  }

  override fun changeArtQuality(quality: ImageQuality) {
    preferencesInteractor.saveImageQuality(quality)
  }

  override fun requestArtQuality() {
    view.showArtQuality(preferencesInteractor.getImageQuality())
  }
}
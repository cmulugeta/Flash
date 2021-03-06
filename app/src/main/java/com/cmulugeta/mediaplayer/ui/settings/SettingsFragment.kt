package com.cmulugeta.mediaplayer.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.R.layout.fragment_settings
import com.cmulugeta.mediaplayer.di.Params
import com.cmulugeta.mediaplayer.domain.model.ImageQuality
import com.cmulugeta.mediaplayer.injectWith
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.view.AlertFlashDialog
import kotlinx.android.synthetic.main.fragment_settings.*
import com.cmulugeta.mediaplayer.ui.settings.SettingsContract.Presenter

class SettingsFragment : BaseFragment(), SettingsContract.View {
  override val layout: Int
    get() = fragment_settings

  override val status: LottieAnimationView
    get() = settingsIcon

  private val presenter : Presenter by injectWith(Params.SETTINGS)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    clearAction.setOnClickListener { clearData() }
    inviteItem.setOnClickListener { invite() }
  }

  private fun invite() {
    val intent = Intent(Intent.ACTION_SEND)
        .putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text))
    intent.type = "text/plain"
    startActivity(Intent.createChooser(intent, getString(R.string.choose_to_share_text)))
  }

  override fun showArtQuality(quality: ImageQuality) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showDataCleared() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun clearData() {
    context?.let {
      AlertFlashDialog.create(it, root, presenter::clearData)
          .setTitle(getString(R.string.alert_clear_data_label))
          .show()
    }
  }
}
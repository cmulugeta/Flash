package com.cmulugeta.mediaplayer.ui.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_actions.*
import android.content.Intent
import com.google.gson.reflect.TypeToken
import com.cmulugeta.mediaplayer.ui.utils.*
import android.annotation.SuppressLint
import com.cmulugeta.mediaplayer.di.Params
import com.cmulugeta.mediaplayer.domain.model.TrackType
import com.cmulugeta.mediaplayer.injectWith
import com.cmulugeta.mediaplayer.then

class ActionsActivity : BaseActivity(), ActionsContract.View {

  override val presenter: ActionsContract.Presenter by injectWith(Params.ACTIONS)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_actions)
    container.setOnClickListener { supportFinishAfterTransition() }
    val bundle = savedInstanceState ?: intent.extras
    val track = bundle.fetchHeavyObject<Track>(Constants.EXTRA_TRACK, object : TypeToken<Track>() {}.type)
    track?.let {
      loadTrack(it)
      setUp(it)
    }
  }

  private fun setUp(track: Track) {
    like.assignTextIf(!track.isLiked, R.string.like_action, R.string.unlike_action)
    history.assignTextIf(!track.isSaved, R.string.add_action, R.string.remove_action)
    like.click {
      track.isLiked.then({ presenter.dislike(track) }, { presenter.like(track) })
    }
    history.click {
      track.isSaved.then({ presenter.remove(track) }, { presenter.add(track) })
    }
    share.click {
      val intent = Intent(Intent.ACTION_SEND)
      val message = getString(R.string.intro_share_message) + track.title +
          getString(R.string.by_label) + track.artist
      intent.putExtra(Intent.EXTRA_TEXT, message)
      intent.type = "text/plain"
      startActivity(Intent.createChooser(intent, getString(R.string.choose_to_share_text)))
    }
  }

  private fun animateText(text: TextView, string: String) {
    text.animate()
        .scaleX(0f)
        .scaleY(0f)
        .alpha(0f)
        .setDuration(100)
        .setListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            text.text = string
            text.alpha = 1f
            text.animate()
                .scaleY(1f)
                .scaleX(1f)
                .setDuration(150)
                .setListener(null)
                .setInterpolator(OvershootInterpolator(2f))
                .start()
          }
        }).start()
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(0, R.anim.slide_out_down)
  }

  override fun showRemoved(type: TrackType) {
    when(type){
      TrackType.Favorite -> animateText(like, getString(R.string.like_action))
      TrackType.History -> animateText(history, getString(R.string.add_action))
    }
  }
  override fun showAdded(type: TrackType) {
    when(type){
      TrackType.Favorite -> animateText(like, getString(R.string.unlike_action))
      TrackType.History -> animateText(history, getString(R.string.remove_action))
    }
  }

  override fun onConnectionError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun error() = container.showMessage(R.string.cleared_message)

  @SuppressLint("SetTextI18n")
  private fun loadTrack(track: Track) {
    Glide.with(this)
        .load(track.artworkUrl)
        .placeholder(R.drawable.placeholder)
        .animate(R.anim.fade_in)
        .into(art)
    artist.text = track.artist
    song.text = track.title
    duration.text = "\u2022 ${track.formattedDuration}"
  }
}

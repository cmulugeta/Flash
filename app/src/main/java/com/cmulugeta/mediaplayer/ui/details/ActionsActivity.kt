package com.cmulugeta.mediaplayer.ui.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cmulugeta.mediaplayer.FlashApp
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_actions.*
import android.content.Intent
import com.google.gson.reflect.TypeToken
import com.cmulugeta.mediaplayer.ui.utils.*
import android.annotation.SuppressLint
import javax.inject.Inject

class ActionsActivity:BaseActivity(),ActionsContract.View{

    private lateinit var presenter: ActionsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_actions)
        container.setOnClickListener{supportFinishAfterTransition()}
        val bundle=savedInstanceState?:intent.extras
        val track=bundle.fetchHeavyObject<Track>(Constants.EXTRA_TRACK,
                object: TypeToken<Track>(){}.type)
        track?.let {
            loadTrack(it)
            setUp(it)
        }
    }

    private fun setUp(track:Track){
        like.assignTextIf(!track.isLiked,R.string.like_action,R.string.unlike_action)
        history.assignTextIf(!track.isSaved,R.string.add_action,R.string.remove_action)
        like.click {
            executeIf(track.isLiked,{presenter.like(track)}, {presenter.dislike(track)})
        }
        history.click {
            executeIf(!track.isSaved,{presenter.add(track)}, {presenter.remove(track)})
        }
        share.click {
            val intent = Intent(Intent.ACTION_SEND)
            val message=getString(R.string.intro_share_message)+track.title+
                    getString(R.string.by_label)+track.artist
            intent.putExtra(Intent.EXTRA_TEXT,message)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, getString(R.string.choose_to_share_text)))
        }
    }

    private fun animateText(text:TextView,string:String){
        text.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(100)
                .setListener(object:AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        text.text=string
                        text.alpha=1f
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
        overridePendingTransition(0,R.anim.slide_out_down)
    }

    override fun added()= animateText(history,getString(R.string.remove_action))

    override fun removed()=animateText(history,getString(R.string.add_action))

    override fun liked()=animateText(like,getString(R.string.unlike_action))

    override fun disliked()=animateText(like,getString(R.string.like_action))

    override fun error()=container.showMessage(R.string.cleared_message)

    @SuppressLint("SetTextI18n")
    private fun loadTrack(track:Track){
        Glide.with(this)
                .load(track.artworkUrl)
                .placeholder(R.drawable.placeholder)
                .animate(R.anim.fade_in)
                .into(art)
        artist.text=track.artist
        song.text=track.title
        duration.text="\u2022 ${track.formattedDuration}"
    }

    override fun inject()=DaggerViewComponent.builder()
            .presenterModule(PresenterModule())
            .applicationComponent(FlashApp.app().component())
            .build().inject(this)

    @Inject
    override fun attach(presenter: ActionsContract.Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }
}

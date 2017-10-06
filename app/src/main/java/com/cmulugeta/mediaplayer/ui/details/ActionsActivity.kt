package com.cmulugeta.mediaplayer.ui.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken
import com.cmulugeta.mediaplayer.FitnessSound
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import com.cmulugeta.mediaplayer.ui.utils.BundleUtils
import com.cmulugeta.mediaplayer.ui.utils.Constants
import kotlinx.android.synthetic.main.fragment_actions.*
import javax.inject.Inject
import android.content.Intent



class ActionsActivity:BaseActivity(),ActionsContract.View{

    private lateinit var presenter: ActionsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_actions)
        val bundle=savedInstanceState?:intent.extras
        container.setOnClickListener{supportFinishAfterTransition()}
        bundle?.let {
            val track= BundleUtils.fetchHeavyObject<Track>(object: TypeToken<Track>() {}.type, it, Constants.EXTRA_TRACK)
            track?.let {
                loadTrack(it)
                like.text=if(!it.isLiked) getString(R.string.like_action)
                    else getString(R.string.dislike_action)
                history.text=if(!it.isSaved) getString(R.string.add_action)
                    else getString(R.string.remove_action)
                like.setOnClickListener {_->
                    if(!it.isLiked) presenter.like(it)
                        else presenter.dislike(it)
                }
                history.setOnClickListener {_->
                    if(!it.isSaved) presenter.add(it)
                        else presenter.remove(it)
                }
                share.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    val message=getString(R.string.intro_share_message)+track.title
                    intent.putExtra(Intent.EXTRA_TEXT,message)
                    intent.type = "text/plain"
                    startActivity(Intent.createChooser(intent, getString(R.string.choose_to_share_text)))
                }

            }
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

    override fun added()=
            animateText(history,getString(R.string.remove_action))

    override fun removed()=
            animateText(history,getString(R.string.add_action))

    override fun liked()=
            animateText(like,getString(R.string.dislike_action))

    override fun disliked() =
            animateText(like,getString(R.string.like_action))

    override fun error()=
            Snackbar.make(container,R.string.cleared_message,2000).show()

    @SuppressLint("SetTextI18n")
    private fun loadTrack(track:Track){
        Glide.with(this)
                .load(track.artworkUrl)
                .placeholder(R.drawable.placeholder)
                .animate(R.anim.fade_in)
                .into(art)
        artist.text=track.artist
        song.text=track.title
        duration.text="\u2022 ${track.formatedDuration}"
    }

    override fun inject()=DaggerViewComponent.builder()
            .presenterModule(PresenterModule())
            .applicationComponent(FitnessSound.app().component())
            .build().inject(this)

    @Inject
    override fun attach(presenter: ActionsContract.Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }
}

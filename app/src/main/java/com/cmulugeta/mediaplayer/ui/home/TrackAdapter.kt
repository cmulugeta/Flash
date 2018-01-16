package com.cmulugeta.mediaplayer.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.playback.QueueManager
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.utils.Constants
import kotlinx.android.synthetic.main.adapter_track_item.view.*
import android.annotation.SuppressLint
import com.cmulugeta.mediaplayer.ui.utils.click
import com.cmulugeta.mediaplayer.ui.utils.packHeavyObject

class TrackAdapter constructor(context: Context, click: (Bundle) -> Unit, val clickMore: (Bundle) -> Unit) :
    BaseAdapter<Track>(context, click) {

  inner class TrackViewHolder constructor(itemView: View) :
      BaseAdapter<Track>.GenericViewHolder(itemView) {
    val title: TextView = itemView.title
    val art: ImageView = itemView.art
    val artist: TextView = itemView.artist
    val duration: TextView = itemView.duration

    init {
      itemView.click {
        val queue = QueueManager(data, adapterPosition)
        click(Bundle().packHeavyObject(Constants.EXTRA_QUEUE, queue))
      }
      itemView.more.setOnClickListener {
        val track = at(adapterPosition)
        clickMore(Bundle().packHeavyObject(Constants.EXTRA_TRACK, track))
      }
      itemView.setOnLongClickListener { itemView.more.performClick() }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindData() {
      val track = at(adapterPosition)
      title.text = track.title
      artist.text = track.artist
      duration.text = "\u2022 ${track.formattedDuration}"
      Glide.with(itemView.context)
          .load(track.artworkUrl)
          .placeholder(R.drawable.placeholder)
          .animate(R.anim.fade_in)
          .into(art)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
      : BaseAdapter<Track>.GenericViewHolder
      = TrackViewHolder(inflate(R.layout.adapter_track_item, parent))
}

package com.cmulugeta.mediaplayer.playback

import android.support.v4.media.MediaMetadataCompat
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.domain.model.Track

class MetadataMapper : Mapper<MediaMetadataCompat, Track>() {
  override fun map(fake: Track?): MediaMetadataCompat? {
    return fake?.let {
      MediaMetadataCompat.Builder()
          .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, fake.artworkUrl)
          .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, fake.title)
          .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, fake.artist)
          .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, fake.duration!!.toLong())
          .putString(MediaMetadataCompat.METADATA_KEY_DATE, fake.releaseDate)
          .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, fake.streamUrl)
          .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, fake.id).build()
    }
  }

  override fun reverse(real: MediaMetadataCompat?): Track? {
    return real?.let {
      val track = Track()
      track.artist = real.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)
      track.artworkUrl = real.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI)
      track.duration = real.getString(MediaMetadataCompat.METADATA_KEY_DURATION)
      track.releaseDate = real.getString(MediaMetadataCompat.METADATA_KEY_DATE)
      track.streamUrl = real.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)
      track.id = real.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
      return track
    }
  }
}

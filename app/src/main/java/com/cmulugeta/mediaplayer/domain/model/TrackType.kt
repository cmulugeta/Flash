package com.cmulugeta.mediaplayer.domain.model

sealed class TrackType {
  object Favorite : TrackType()
  object History : TrackType()
  object All : TrackType()
}
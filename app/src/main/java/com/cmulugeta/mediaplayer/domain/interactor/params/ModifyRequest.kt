package com.cmulugeta.mediaplayer.domain.interactor.params

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.domain.model.TrackType

data class ModifyRequest(val type: TrackType, val track: Track)
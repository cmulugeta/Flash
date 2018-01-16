package com.cmulugeta.mediaplayer.domain.playback

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.then
import java.util.Collections

class QueueManager(private var tracks: MutableList<Track>, var index: Int) {

  fun next() = !hasNext() then (current()) ?: tracks[++index]

  fun previous() = !hasPrevious() then (current()) ?: tracks[--index]

  fun shuffle() = Collections.shuffle(tracks)

  fun hasNext() = tracks.size > (index + 1)

  fun hasPrevious() = index - 1 >= 0

  fun size() = tracks.size

  fun addTrack(track: Track) = tracks.add(track)

  fun current() = tracks[index]
}
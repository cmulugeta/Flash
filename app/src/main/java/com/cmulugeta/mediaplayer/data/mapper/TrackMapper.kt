package com.cmulugeta.mediaplayer.data.mapper

import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.model.TrackEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackMapper @Inject
constructor():Mapper<Track,TrackEntity>(){
    override fun map(fake: TrackEntity?): Track? {
        return fake?.let {
            val real=Track()
            real.artworkUrl=fake.artwork_url
            real.artist=fake.user?.username
            real.title=fake.title
            real.duration=fake.duration
            real.releaseDate=fake.release
            real.id=fake.id
            real.streamUrl=fake.stream_url
            return real
        }
    }
    override fun reverse(real: Track?): TrackEntity? {
        return real?.let {
            val fake=TrackEntity()
            fake.title=real.title
            fake.artwork_url=real.artworkUrl
            fake.duration=real.duration
            fake.release=real.releaseDate
            fake.id=real.id
            fake.stream_url=real.streamUrl
            return fake
        }
    }
}

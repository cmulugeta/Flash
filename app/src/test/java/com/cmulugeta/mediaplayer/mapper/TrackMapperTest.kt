package com.cmulugeta.mediaplayer.mapper

import com.cmulugeta.mediaplayer.FakeDataProvider
import com.cmulugeta.mediaplayer.data.mapper.TrackMapper
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.model.TrackEntity
import org.junit.runners.JUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnit4::class)
class TrackMapperTest {

    private val mapper=TrackMapper()

    @Test
    fun mapsFakeToReal(){
        val fake=FakeDataProvider.buildTrackEntity()
        assertEqual(mapper.map(fake),fake)
    }

    @Test
    fun mapsRealToFake(){
        val real=FakeDataProvider.buildTrack()
        assertEqual(real,mapper.reverse(real))
    }

    @Test
    fun testsNullInput(){
        val fake:TrackEntity?=null
        val real:Track?=null
        assertEqual(mapper.map(fake),mapper.reverse(real))
    }

    private fun assertEqual(real: Track?,fake:TrackEntity?){
        if(real==null || fake==null){
            assertEquals(real, fake)
            return
        }
        assertEquals(real.id,fake.id)
        assertEquals(real.artworkUrl,fake.artwork_url)
        assertEquals(real.duration,fake.duration)
        assertEquals(real.releaseDate,fake.release)
        assertEquals(real.streamUrl,fake.stream_url)
        assertEquals(real.title,fake.title)
    }
}
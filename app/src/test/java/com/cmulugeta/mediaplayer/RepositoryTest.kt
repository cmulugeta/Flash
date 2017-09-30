package com.cmulugeta.mediaplayer

import com.cmulugeta.mediaplayer.data.Filter
import com.cmulugeta.mediaplayer.data.MusicRepository
import com.cmulugeta.mediaplayer.data.local.TrackHandler
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.TrackEntity
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest{

    @Mock lateinit var mapper: Mapper<Track, TrackEntity>
    @Mock lateinit var service:SoundCloudService
    @Mock lateinit var handler:TrackHandler
    @Mock lateinit var filter: Filter
    @Mock lateinit var scheduler:SchedulerProvider

    @InjectMocks lateinit var repository:MusicRepository

    @Before
    fun setUp(){
        `when`(scheduler.io()).thenReturn(Schedulers.trampoline())
        `when`(scheduler.ui()).thenReturn(Schedulers.trampoline())
        `when`(handler.queryHistory()).thenReturn(FakeDataProvider.buildList(10,{Track()}))
        `when`(handler.queryLoved()).thenReturn(FakeDataProvider.buildList(10,{Track()}))
    }

    @Test
    fun trySomething(){

    }
}
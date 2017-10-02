package com.cmulugeta.mediaplayer.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import com.cmulugeta.mediaplayer.FakeDataProvider
import com.cmulugeta.mediaplayer.data.Filter
import com.cmulugeta.mediaplayer.data.MusicRepository
import com.cmulugeta.mediaplayer.data.local.TrackHandler
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.data.mapper.TrackMapper
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.domain.executor.TestScheduler
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.TrackEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest{

    private var mapper=mock<Mapper<Track,TrackEntity>>()
    private var service=mock<SoundCloudService>()
    private var handler= mock<TrackHandler>()
    private var filter=mock<Filter>()
    private val scheduler=TestScheduler()
    private lateinit var repository:MusicRepository

    @Before
    fun setUp(){
        repository= MusicRepository(mapper,service,handler,filter, scheduler)
        whenever(handler.queryHistory()).thenReturn(FakeDataProvider.buildList(10,{Track()}))
        whenever(handler.queryLoved()).thenReturn(FakeDataProvider.buildList(10,{Track()}))
    }

    @Test
    fun fetchesHistory(){
        repository.fetchHistory()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe()

    }
}
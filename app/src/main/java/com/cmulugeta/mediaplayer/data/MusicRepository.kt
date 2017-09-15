package com.cmulugeta.mediaplayer.data

import android.util.Log
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.Page
import com.cmulugeta.soundcloud.model.TrackEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(val mapper: Mapper<Track,TrackEntity>,
                                          val service:SoundCloudService,
                                          val filter:Filter):Repository{

    private var page:Page<TrackEntity>?=null

    override fun fetchHistory(): Single<List<Track>> {
        return query("imagine dragons") as Single<List<Track>>
    }

    override fun fetchLiked(): Single<List<Track>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(query: String?): Single<List<Track?>> {
        return service.searchTracksPage(TrackEntity.Filter
                .start().byName(query)
                .withPagination()
                .limit(100)
                .createOptions())
                .map({result->
                    page=result
                    result.collection
                }).map(filter::filter)
                .map(mapper::map)
    }

    override fun nextPage(): Single<List<Track?>> {
        if(page!=null){
            return service.searchTracksPage(TrackEntity.Filter
                    .start().nextPage(page)
                    .withPagination()
                    .limit(100)
                    .createOptions())
                    .map({result->
                        page=result
                        result.collection
                    })
                    .map(mapper::map)
        }
        return Single.error(IllegalArgumentException("No more data"))
    }

    override fun like(track: Track?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearHistory(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearLoved(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeLoved(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeRecent(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertRecent(track: Track?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

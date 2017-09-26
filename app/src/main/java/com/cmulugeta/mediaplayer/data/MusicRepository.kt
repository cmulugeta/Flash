package com.cmulugeta.mediaplayer.data


import com.cmulugeta.mediaplayer.data.local.TrackHandler
import com.cmulugeta.mediaplayer.data.mapper.Mapper
import com.cmulugeta.mediaplayer.domain.Repository
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.soundcloud.SoundCloudService
import com.cmulugeta.soundcloud.model.Page
import com.cmulugeta.soundcloud.model.TrackEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject
constructor(val mapper: Mapper<Track,TrackEntity>, val service:SoundCloudService,
            val handler: TrackHandler, val filter:Filter,scheduler: SchedulerProvider):Repository{

    private var page:Page<TrackEntity>?=null
    private var likeSet=HashSet<String>()
    private var recentSet=HashSet<String>()

    init {
        Single.fromCallable({handler.queryHistory()})
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({list->convertToSet(recentSet,list)})
        Single.fromCallable({handler.queryLoved()})
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({list->convertToSet(likeSet,list)})
    }
    override fun fetchHistory():Single<List<Track>>
            = Single.fromCallable({handler.queryHistory()})

    override fun fetchLiked():Single<List<Track>>
            = Single.fromCallable({handler.queryLoved()})

    override fun query(query: String?): Single<List<Track>> {
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
                .map(this::filter)
    }

    override fun nextPage(): Single<List<Track>> {
        if(page!=null){
            return service.searchTracksPage(TrackEntity.Filter
                    .start().nextPage(page)
                    .withPagination()
                    .limit(100)
                    .createOptions())
                    .map({result->
                        page=result
                        result.collection
                    }).map(filter::filter)
                    .map(mapper::map)
                    .map(this::filter)
        }
        return Single.error(IllegalArgumentException("No more data"))
    }

    override fun like(track: Track?):Completable=
            Completable.fromCallable({handler.update(love(track,true))})

    override fun clearHistory():Completable=
            Completable.fromCallable({handler.deleteHistory()})

    override fun clearLoved():Completable=
            Completable.fromCallable({handler.deleteLoved()})

    override fun removeLoved(track: Track):Completable=
            Completable.fromCallable({handler.update(love(track,false))})

    override fun removeRecent(track: Track):Completable=
            Completable.fromCallable({handler.update(save(track,false))})

    override fun insertRecent(track: Track?):Completable=
            Completable.fromCallable({handler.update(save(track,true))})

    private fun convertToSet(set:HashSet<String>, list:List<Track>)=list.forEach{
        it.id?.let {set.add(it)}
    }

    private fun filter(list: List<Track>?)=list?.let{
        it.forEach{track->
            track.isSaved=likeSet.contains(track.id)
            track.isLiked=likeSet.contains(track.id)
        }
        it
    }

    private fun save(track:Track?,saved:Boolean)=track?.let {
        if(!saved) recentSet.remove(track.id)
        else track.id?.let{recentSet.add(it)}
        it.isSaved=saved
        it
    }

    private fun love(track:Track?,liked:Boolean)=track?.let {
        if(!liked) likeSet.remove(track.id)
        else track.id?.let{likeSet.add(it)}
        it.isLiked=liked
        it
    }
}

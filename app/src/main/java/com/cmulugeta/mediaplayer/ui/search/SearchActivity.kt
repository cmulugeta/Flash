package com.cmulugeta.mediaplayer.ui.search

import android.os.Bundle
import android.view.View
import com.cmulugeta.mediaplayer.FitnessSound
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.di.component.DaggerViewComponent
import com.cmulugeta.mediaplayer.di.module.PresenterModule
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.home.TrackAdapter
import com.cmulugeta.mediaplayer.ui.search.SearchContract.Presenter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity:BaseActivity(), SearchContract.View{

    private lateinit var presenter:Presenter
    private lateinit var adapter:BaseAdapter<Track>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        adapter=TrackAdapter(this,eventBus)
    }

    override fun inject(){
        DaggerViewComponent.builder()
                .presenterModule(PresenterModule())
                .applicationComponent(FitnessSound.app().component())
                .build().inject(this)
    }

    override fun handleEvent(event: Any) {
    }

    override fun show(list: List<Track>)=adapter.set(list.toMutableList())

    override fun append(list: List<Track>)=adapter.appendData(list.toMutableList())

    override fun error() {
    }

    override fun empty() {
    }

    override fun setLoading(isLoading: Boolean) {
        progress.visibility=if(isLoading)
            View.VISIBLE else View.GONE
    }

    override fun attach(presenter: Presenter) {
        this.presenter=presenter
        presenter.attachView(this)
    }
}
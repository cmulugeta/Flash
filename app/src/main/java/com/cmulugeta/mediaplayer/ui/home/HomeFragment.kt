package com.cmulugeta.mediaplayer.ui.home

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import butterknife.BindView

abstract class HomeFragment: BaseFragment(),HomeContract.View{

    protected lateinit var presenter: Presenter
    protected lateinit var adapter:BaseAdapter<Track>

    @BindView(R.id.list)
    lateinit var list: RecyclerView

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(view!=null){
            adapter=TrackAdapter(context,rxBus)
            list.adapter=adapter
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun show(list: List<Track>) {
        adapter.set(list.toMutableList())
    }

    override fun error() {

    }

    override fun cleared() {

    }

    override fun empty() {

    }

    override fun removed(track: Track) {

    }

    override fun setLoading(isLoading: Boolean) {

    }

    override fun layoutId()= R.layout.fragment_home
}

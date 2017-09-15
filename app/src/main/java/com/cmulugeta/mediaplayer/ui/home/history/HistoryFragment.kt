package com.cmulugeta.mediaplayer.ui.home.history

import android.support.v7.widget.RecyclerView
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.HomeContract
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.di.qualifier.History
import javax.inject.Inject
import butterknife.BindView

class HistoryFragment : BaseFragment(), HomeContract.View {

    private lateinit var presenter:Presenter
    private lateinit var adapter:BaseAdapter<Track>

    @BindView(R.id.list)
    lateinit var list:RecyclerView


    override fun show(list: List<Track>) {
        adapter.set(list.toMutableList())
    }

    override fun error() {

    }

    override fun empty() {

    }

    override fun setLoading(isLoading: Boolean) {

    }

    override fun layoutId()=R.layout.fragment_home

    @Inject
    override fun attach(@History presenter: Presenter) {
        this.presenter=presenter
        presenter.attach(this)
    }

    override fun inject() {
    }
}

package com.cmulugeta.mediaplayer.ui.home

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.*
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.domain.model.Track
import com.cmulugeta.mediaplayer.ui.base.BaseAdapter
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.HomeContract.Presenter
import kotlinx.android.synthetic.main.fragment_home.*

abstract class HomeFragment: BaseFragment(),HomeContract.View{

    protected lateinit var presenter: Presenter
    protected lateinit var adapter:BaseAdapter<Track>

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        view?.let {
            refresher.setOnRefreshListener { presenter.start() }
            adapter=TrackAdapter(context,{navigator.navigate(activity,it)}, {navigator.actions(activity,it)})
            list.adapter=adapter
        }
    }

    fun adjustPosition(position:Int){
        list.scrollToPosition(position)
        postponeEnterTransition()
        list.viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                list.viewTreeObserver.removeOnPreDrawListener(this)
                list.requestLayout()
                startPostponedEnterTransition()
                return true
            }
        })
    }

    fun fetchSharedElement(position:Int):View?
            =list.findViewWithTag(getString(R.string.art_trans)+position)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.home,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.delete){
            AlertDialog.Builder(context)
                    .setTitle(R.string.erase_label)
                    .setMessage(alertMessage())
                    .setPositiveButton(getString(R.string.yes_label),{_,_->presenter.clear()})
                    .setNegativeButton(getString(R.string.no_label),{dialog,_->dialog.dismiss()})
                    .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun error() {
        setMenuVisibility(false)
        empty.visibility=View.VISIBLE
    }

    override fun empty(){
        adapter.clear()
        setMenuVisibility(false)
        empty.visibility=View.VISIBLE
    }

    override fun setLoading(isLoading: Boolean){
        refresher.isRefreshing=isLoading
    }

    abstract fun alertMessage():String

    override fun show(list: List<Track>){
        empty.visibility=View.GONE
        adapter.set(list.toMutableList())
    }

    override fun layoutId()= R.layout.fragment_home

    override fun removed(track: Track)= showMessage(R.string.removed_message)

    override fun cleared(){
        empty()
        showMessage(R.string.cleared_message)
    }
}

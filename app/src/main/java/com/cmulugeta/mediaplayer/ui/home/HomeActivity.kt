package com.cmulugeta.mediaplayer.ui.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.ui.base.BaseActivity
import com.cmulugeta.mediaplayer.ui.base.BaseFragment
import com.cmulugeta.mediaplayer.ui.home.history.HistoryFragment
import com.cmulugeta.mediaplayer.ui.home.loved.LovedFragment

class HomeActivity : BaseActivity() {

    @BindView(R.id.drawer)
    lateinit var drawer:DrawerLayout

    @BindView(R.id.navigation)
    lateinit var navigation:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        navigation.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.history->load(HistoryFragment())
                R.id.loved->load(LovedFragment())
                else ->false
            }
        }
    }

    private fun load(fragment: BaseFragment):Boolean{
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame,fragment)
                .commit()
        drawer.closeDrawers()
        return true
    }

    override fun inject() {

    }

    override fun handleEvent(event: Any) {

    }
}

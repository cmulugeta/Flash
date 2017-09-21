package com.cmulugeta.mediaplayer.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.cmulugeta.mediaplayer.ui.search.SearchActivity
import javax.inject.Inject
import javax.inject.Singleton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import com.cmulugeta.mediaplayer.ui.details.ActionsActivity
import com.cmulugeta.mediaplayer.ui.player.PlayerActivity


@Singleton
class Navigator @Inject constructor(){

    fun navigate(activity: Activity, bundle:Bundle){
        val intent= Intent(activity,PlayerActivity::class.java)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    fun search(activity:Activity, pair:Pair<View,String>){
        val intent= Intent(activity,SearchActivity::class.java)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            val optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, pair)
            activity.startActivity(intent, optionsCompat.toBundle())
        }else activity.startActivity(intent)
    }

    fun actions(activity: Activity, bundle: Bundle){
        val intent= Intent(activity,ActionsActivity::class.java)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }
}

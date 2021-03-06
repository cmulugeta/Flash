package com.cmulugeta.mediaplayer.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cmulugeta.mediaplayer.ui.search.SearchActivity
import android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import android.support.v4.util.Pair
import com.cmulugeta.mediaplayer.R
import com.cmulugeta.mediaplayer.ui.details.ActionsActivity
import com.cmulugeta.mediaplayer.ui.player.PlayerActivity

class Navigator {

  fun navigate(activity: Activity, bundle: Bundle) {
    val intent = Intent(activity, PlayerActivity::class.java)
    intent.putExtras(bundle)
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.slide_out_up, 0)
  }

  fun search(activity: Activity, pair: Pair<View, String>) {
    val intent = Intent(activity, SearchActivity::class.java)
    val optionsCompat = makeSceneTransitionAnimation(activity, pair)
    activity.startActivity(intent, optionsCompat.toBundle())
  }

  fun actions(activity: Activity, bundle: Bundle) {
    val intent = Intent(activity, ActionsActivity::class.java)
    intent.putExtras(bundle)
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.slide_out_up, 0)
  }
}

package com.cmulugeta.mediaplayer

import android.support.v7.widget.RecyclerView
import com.cmulugeta.mediaplayer.ui.utils.OnReachBottomListener
import io.reactivex.Single

infix fun <T> String?.ifNotEmpty(value: T)
    = if (isNullOrEmpty()) value else null

infix fun <T> Boolean.then(param: T): T?
    = if (this) param else null

fun <T> Boolean.then(result: T, default: T)
    = if (this) result else default

inline fun Boolean.then(expression: () -> Unit)
    = if (this) expression() else {
}

inline infix fun <T> Boolean.then(expression: () -> T)
    = if (this) expression() else null

inline fun <T> Boolean.then(expression: () -> T, default: () -> T)
    = if (this) expression() else default()

inline fun <T, Type> Type?.ifNotNull(source: (Type) -> T, default: T)
    = if (this != null) source(this) else default

infix inline fun <T, Type> Type?.notNullThen(source: (Type) -> T) = if (this != null) source(this) else null

fun <T> wrongArgument() = Single.error<T>(IllegalArgumentException())

inline fun RecyclerView.addReachBottomListener(crossinline callback: () -> Unit) {
  addOnScrollListener(object : OnReachBottomListener(layoutManager) {
    override fun onLoadMore() {
      callback()
    }
  })
}
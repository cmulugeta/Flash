package com.cmulugeta.mediaplayer.ui.search

interface QueryCallback {
  fun queryTyped(query: String?)
  fun inputCleared()
}
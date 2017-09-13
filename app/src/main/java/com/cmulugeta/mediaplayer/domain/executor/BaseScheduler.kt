package com.cmulugeta.mediaplayer.domain.executor

import io.reactivex.Scheduler

interface BaseScheduler{
    fun io():Scheduler
    fun ui():Scheduler
}

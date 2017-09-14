package com.cmulugeta.mediaplayer.di.module


import android.content.Context
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideContext()=context

    @Singleton
    @Provides
    fun provideScheduler():BaseScheduler=SchedulerProvider()
}

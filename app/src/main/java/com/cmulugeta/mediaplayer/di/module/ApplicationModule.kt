package com.cmulugeta.mediaplayer.di.module


import android.content.Context
import com.cmulugeta.mediaplayer.domain.executor.BaseScheduler
import com.cmulugeta.mediaplayer.domain.executor.SchedulerProvider
import com.cmulugeta.mediaplayer.ui.base.Navigator
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {
    @Singleton
    @Provides
    internal fun provideContext()=context

    @Singleton
    @Provides
    internal fun provideScheduler():BaseScheduler=SchedulerProvider()

    @Singleton
    @Provides
    internal fun provideNavigator()=Navigator()
}

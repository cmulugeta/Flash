package com.cmulugeta.mediaplayer.di.component

import com.cmulugeta.mediaplayer.di.module.InteractorModule
import dagger.Component

@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(InteractorModule::class))
interface ViewComponent{

}
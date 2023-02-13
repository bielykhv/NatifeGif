package com.example.natifegif.di

import android.app.Application
import com.example.natifegif.presentation.GifActivity
import com.example.natifegif.presentation.GifListFragment
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun infect(frag: GifListFragment)
    fun inject(activity: GifActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance  application: Application
        ): ApplicationComponent
    }



}

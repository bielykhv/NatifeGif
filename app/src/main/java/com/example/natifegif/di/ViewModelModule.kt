package com.example.natifegif.di

import androidx.lifecycle.ViewModel
import com.example.natifegif.presentation.ActivityViewModel
import com.example.natifegif.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    fun bindActivityViewModel(viewModel: ActivityViewModel): ViewModel
}
package com.example.natifegif.di

import android.app.Application
import com.example.natifegif.data.*
import com.example.natifegif.data.database.Dao
import com.example.natifegif.data.database.MainDb
import com.example.natifegif.data.network.ApiFactory
import com.example.natifegif.data.network.ApiService
import com.example.natifegif.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository


    companion object {
        @Provides
        fun provideDao(application: Application): Dao {
            return MainDb.getDataBase(application).getDao()

        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }


}
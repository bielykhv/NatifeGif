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
    @ApplicationScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideDao(application: Application): Dao {
            return MainDb.getDataBase(application).getDao()

        }
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }


}
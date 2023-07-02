package com.dev_marinov.kinopoiskapp.di

import android.content.Context
import com.dev_marinov.kinopoiskapp.data.movie.local.datastore.DataStoreRepositoryImpl
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository =
        DataStoreRepositoryImpl(context)
}
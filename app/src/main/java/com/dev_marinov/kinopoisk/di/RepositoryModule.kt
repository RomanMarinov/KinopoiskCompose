package com.dev_marinov.kinopoisk.di

import com.dev_marinov.kinopoisk.data.movie.MovieRepositoryImpl
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(kinopoiskRepositoryImpl: MovieRepositoryImpl) : MovieRepository
}
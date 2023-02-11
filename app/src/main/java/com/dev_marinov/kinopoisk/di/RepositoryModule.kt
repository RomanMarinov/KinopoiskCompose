package com.dev_marinov.kinopoisk.di

import com.dev_marinov.kinopoisk.data.common.RepositoryMediatorImpl
import com.dev_marinov.kinopoisk.data.movie.MovieRepositoryImpl
import com.dev_marinov.kinopoisk.domain.repository.RepositoryMediator
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
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindCommonRepository(commonRepositoryImpl: RepositoryMediatorImpl): RepositoryMediator
}
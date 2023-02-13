package com.dev_marinov.kinopoisk.di

import com.dev_marinov.kinopoisk.data.repositoryCoordinator.RepositoryCoordinatorImpl
import com.dev_marinov.kinopoisk.data.movie.MovieRepositoryImpl
import com.dev_marinov.kinopoisk.data.poster.PosterRepositoryImpl
import com.dev_marinov.kinopoisk.data.rating.RatingRepositoryImpl
import com.dev_marinov.kinopoisk.data.releaseYear.ReleaseYearRepositoryImpl
import com.dev_marinov.kinopoisk.data.votes.VotesRepositoryImpl
import com.dev_marinov.kinopoisk.domain.repository.RepositoryCoordinator
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import com.dev_marinov.kinopoisk.domain.repository.PosterRepository
import com.dev_marinov.kinopoisk.domain.repository.RatingRepository
import com.dev_marinov.kinopoisk.domain.repository.ReleaseYearRepository
import com.dev_marinov.kinopoisk.domain.repository.VotesRepository
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
    abstract fun bindPosterRepository(posterRepositoryImpl: PosterRepositoryImpl): PosterRepository

    @Binds
    @Singleton
    abstract fun bindRatingRepository(ratingRepositoryImpl: RatingRepositoryImpl): RatingRepository

    @Binds
    @Singleton
    abstract fun bindReleaseYearRepository(releaseYearRepositoryImpl: ReleaseYearRepositoryImpl): ReleaseYearRepository

    @Binds
    @Singleton
    abstract fun bindVotesRepository(votesRepositoryImpl: VotesRepositoryImpl): VotesRepository

    @Binds
    @Singleton
    abstract fun bindCommonRepository(commonRepositoryImpl: RepositoryCoordinatorImpl): RepositoryCoordinator
}
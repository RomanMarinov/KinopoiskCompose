package com.dev_marinov.kinopoiskapp.di

import com.dev_marinov.kinopoiskapp.data.movie.MovieRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.poster.PosterRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.rating.RatingRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.releaseYear.ReleaseYearRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.RepositoryCoordinatorImpl
import com.dev_marinov.kinopoiskapp.data.votes.VotesRepositoryImpl
import com.dev_marinov.kinopoiskapp.domain.repository.*
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
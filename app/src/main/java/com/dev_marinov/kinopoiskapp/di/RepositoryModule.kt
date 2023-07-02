package com.dev_marinov.kinopoiskapp.di

import com.dev_marinov.kinopoiskapp.data.favorite.FavoriteRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.genres.GenresRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.movie.MovieRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.persons.remote.PersonsRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.poster.PosterRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.rating.RatingRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.releaseYear.ReleaseYearRepositoryImpl
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.RepositoryCoordinatorImpl
import com.dev_marinov.kinopoiskapp.data.video.VideosRepositoryImpl
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
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository

    @Binds
    @Singleton
    abstract fun bindPosterRepository(posterRepositoryImpl: PosterRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository

    @Binds
    @Singleton
    abstract fun bindRatingRepository(ratingRepositoryImpl: RatingRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository

    @Binds
    @Singleton
    abstract fun bindReleaseYearRepository(releaseYearRepositoryImpl: ReleaseYearRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.ReleaseYearRepository

    @Binds
    @Singleton
    abstract fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.GenresRepository

    @Binds
    @Singleton
    abstract fun bindVotesRepository(votesRepositoryImpl: VotesRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.VotesRepository

    @Binds
    @Singleton
    abstract fun bindPersonsRepository(personsRepositoryImpl: PersonsRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.PersonsRepository

    @Binds
    @Singleton
    abstract fun bindVideosRepository(videosRepositoryImpl: VideosRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindCommonRepository(commonRepositoryImpl: RepositoryCoordinatorImpl): com.dev_marinov.kinopoiskapp.domain.repository.RepositoryCoordinator
}
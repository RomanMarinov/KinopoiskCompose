package com.dev_marinov.kinopoiskapp.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.dev_marinov.kinopoiskapp.data.AppDatabase
import com.dev_marinov.kinopoiskapp.data.favorite.FavoriteDao
import com.dev_marinov.kinopoiskapp.data.genres.local.GenresDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsDao
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterDao
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingDao
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearDao
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local.CoordinatorDao
import com.dev_marinov.kinopoiskapp.data.video.local.VideosDao
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideReleaseYearDao(appDatabase: AppDatabase): ReleaseYearDao =
        appDatabase.releaseYearDao()

    @Provides
    @Singleton
    fun provideVotesDao(appDatabase: AppDatabase): VotesDao = appDatabase.votesDao()

    @Provides
    @Singleton
    fun provideRatingDao(appDatabase: AppDatabase): RatingDao = appDatabase.ratingDao()

    @Provides
    @Singleton
    fun providePosterDao(appDatabase: AppDatabase): PosterDao = appDatabase.posterDao()

    @Provides
    @Singleton
    fun provideGenresDao(appDatabase: AppDatabase): GenresDao = appDatabase.genresDao()

    @Provides
    @Singleton
    fun providePersonsDao(appDatabase: AppDatabase): PersonsDao = appDatabase.personsDao()

    @Provides
    @Singleton
    fun provideVideosDao(appDatabase: AppDatabase): VideosDao = appDatabase.videosDao()

    @Provides
    @Singleton
    fun provideCommonDao(appDatabase: AppDatabase): CoordinatorDao = appDatabase.commonDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    }

}
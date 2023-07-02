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
    fun provideMovieDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideReleaseYearDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): ReleaseYearDao =
        appDatabase.releaseYearDao()

    @Provides
    @Singleton
    fun provideVotesDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): VotesDao = appDatabase.votesDao()

    @Provides
    @Singleton
    fun provideRatingDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): RatingDao = appDatabase.ratingDao()

    @Provides
    @Singleton
    fun providePosterDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): PosterDao = appDatabase.posterDao()

    @Provides
    @Singleton
    fun provideGenresDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): GenresDao = appDatabase.genresDao()

    @Provides
    @Singleton
    fun providePersonsDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): PersonsDao = appDatabase.personsDao()

    @Provides
    @Singleton
    fun provideVideosDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): VideosDao = appDatabase.videosDao()

    @Provides
    @Singleton
    fun provideCommonDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): CoordinatorDao = appDatabase.commonDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: com.dev_marinov.kinopoiskapp.data.AppDatabase): FavoriteDao = appDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): com.dev_marinov.kinopoiskapp.data.AppDatabase {
        return databaseBuilder(
            context,
            com.dev_marinov.kinopoiskapp.data.AppDatabase::class.java,
            com.dev_marinov.kinopoiskapp.data.AppDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    }
}
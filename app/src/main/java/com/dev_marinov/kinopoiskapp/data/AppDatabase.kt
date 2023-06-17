package com.dev_marinov.kinopoiskapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev_marinov.kinopoiskapp.data.favorite.FavoriteEntity
import com.dev_marinov.kinopoiskapp.data.favorite.FavoriteDao
import com.dev_marinov.kinopoiskapp.data.genres.local.GenresDao
import com.dev_marinov.kinopoiskapp.data.genres.local.GenresEntity
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsDao
import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsEntity
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterDao
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingDao
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingEntity
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearDao
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local.CoordinatorDao
import com.dev_marinov.kinopoiskapp.data.video.local.VideosDao
import com.dev_marinov.kinopoiskapp.data.video.local.VideosEntity
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesDao
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesEntity

@Database(
    entities = [
        MovieEntity::class,
        PosterEntity::class,
        RatingEntity::class,
        ReleaseYearEntity::class,
        VotesEntity::class,
        GenresEntity::class,
        PersonsEntity::class,
        VideosEntity::class,
        FavoriteEntity::class
    ],
    version = 1
)

@TypeConverters(DataConvertersForList::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "kinopoisk_database"
    }

    abstract fun movieDao(): MovieDao
    abstract fun releaseYearDao(): ReleaseYearDao
    abstract fun votesDao(): VotesDao
    abstract fun ratingDao(): RatingDao
    abstract fun posterDao(): PosterDao
    abstract fun genresDao(): GenresDao
    abstract fun personsDao(): PersonsDao
    abstract fun videosDao(): VideosDao
    abstract fun commonDao(): CoordinatorDao
    abstract fun favoriteDao(): FavoriteDao
}
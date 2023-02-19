package com.dev_marinov.kinopoiskapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterDao
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingDao
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingEntity
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearDao
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local.CoordinatorDao
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesDao
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesEntity

@Database(
    entities = [
        MovieEntity::class,
        PosterEntity::class,
        RatingEntity::class,
        ReleaseYearEntity::class,
        VotesEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "kinopoisk_database"
    }

    abstract fun movieDao(): MovieDao
    abstract fun releaseYearDao(): ReleaseYearDao
    abstract fun votesDao(): VotesDao
    abstract fun ratingDao(): RatingDao
    abstract fun posterDao(): PosterDao
    abstract fun commonDao(): CoordinatorDao
}
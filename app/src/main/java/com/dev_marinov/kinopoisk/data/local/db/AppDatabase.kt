package com.dev_marinov.kinopoisk.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_marinov.kinopoisk.data.local.entities.*

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
    abstract fun kinopoiskDao(): KinopoiskDao
}
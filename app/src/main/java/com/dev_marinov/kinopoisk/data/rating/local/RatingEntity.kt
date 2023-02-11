package com.dev_marinov.kinopoisk.data.rating.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity

@Entity(
    tableName = "ratings",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["rating_id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RatingEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val await: Double,
    val filmCritics: Int,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)
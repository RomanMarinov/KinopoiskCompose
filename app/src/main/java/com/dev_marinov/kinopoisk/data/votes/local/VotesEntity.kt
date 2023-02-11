package com.dev_marinov.kinopoisk.data.votes.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity

@Entity(
    tableName = "votes",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["votes_id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VotesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val await: Int,
    val filmCritics: Int,
    val imdb: Int,
    val kp: Int,
    val russianFilmCritics: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)
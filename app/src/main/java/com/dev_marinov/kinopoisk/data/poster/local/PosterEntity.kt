package com.dev_marinov.kinopoisk.data.poster.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity

@Entity(
    tableName = "posters",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["release_years_ids"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PosterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    val url: String,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)
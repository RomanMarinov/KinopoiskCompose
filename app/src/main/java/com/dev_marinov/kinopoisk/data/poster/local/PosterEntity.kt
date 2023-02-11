package com.dev_marinov.kinopoisk.data.poster.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.domain.model.Poster

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
) {
    companion object {

        fun mapFromDomain(poster: Poster): PosterEntity = PosterEntity(
            id = poster.id,
            previewUrl = poster.previewUrl,
            url = poster.url,
            movieId = poster.movieId
        )
    }

    fun mapToDomain(): Poster = Poster(
        id = id,
        previewUrl = previewUrl,
        url = url,
        movieId = movieId
    )
}
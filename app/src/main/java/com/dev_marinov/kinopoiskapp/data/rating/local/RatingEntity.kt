package com.dev_marinov.kinopoiskapp.data.rating.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.Rating

@Entity(
    tableName = "ratings",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val await: Double,
    val filmCritics: Int,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double,
    @ColumnInfo(name = "movie_id")
    val movieId: Int?
) {
    companion object {

        fun mapFromDomain(rating: Rating): RatingEntity = RatingEntity(
            id = rating.id,
            await = rating.await,
            filmCritics = rating.filmCritics,
            imdb = rating.imdb,
            kp = rating.kp,
            russianFilmCritics = rating.russianFilmCritics,
            movieId = rating.movieId
        )
    }

    fun mapToDomain(): Rating = Rating(
        id = id,
        await = await,
        filmCritics = filmCritics,
        imdb = imdb,
        kp = kp,
        russianFilmCritics = russianFilmCritics,
        movieId = movieId
    )
}
package com.dev_marinov.kinopoiskapp.data.votes.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.Votes

@Entity(
    tableName = "votes",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
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
) {

    companion object {

        fun mapFromDomain(votes: Votes): VotesEntity = VotesEntity(
            id = votes.id,
            await = votes.await,
            filmCritics = votes.filmCritics,
            imdb = votes.imdb,
            kp = votes.kp,
            russianFilmCritics = votes.russianFilmCritics,
            movieId = votes.movieId
        )
    }

    fun mapToDomain(): Votes = Votes(
        id = id,
        await = await,
        filmCritics = filmCritics,
        imdb = imdb,
        kp = kp,
        russianFilmCritics = russianFilmCritics,
        movieId = movieId
    )
}
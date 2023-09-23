package com.dev_marinov.kinopoiskapp.data.votes.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.movie.Votes

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
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kp: Int,
    val imdb: Int,
    val filmCritics: Int,
    val russianFilmCritics: Int,
    val await: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
) {

    companion object {

        fun mapFromDomain(votes: Votes): VotesEntity = VotesEntity(
            id = votes.id,
            kp = votes.kp,
            imdb = votes.imdb,
            filmCritics = votes.filmCritics,
            russianFilmCritics = votes.russianFilmCritics,
            await = votes.await,
            movieId = votes.movieId
        )
    }

    fun mapToDomain(): Votes = Votes(
        id = id,
        kp = kp,
        imdb = imdb,
        filmCritics = filmCritics,
        russianFilmCritics = russianFilmCritics,
        await = await,
        movieId = movieId
    )
}
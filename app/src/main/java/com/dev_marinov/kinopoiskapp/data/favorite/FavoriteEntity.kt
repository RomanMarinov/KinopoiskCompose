package com.dev_marinov.kinopoiskapp.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dev_marinov.kinopoiskapp.data.DataConvertersForList
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import com.dev_marinov.kinopoiskapp.presentation.model.SelectableFavoriteMovie

@Entity(tableName = "favorite_movie")
@TypeConverters(DataConvertersForList::class)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val poster: Poster?,
    val rating: Rating?,
    val votes: Votes?,
    val genres: List<Genres>,
    val persons: List<Person>,
    val videos: Videos?,
    val isFavorite: Boolean,
    var timestamp: Long = System.currentTimeMillis() // время добавления
) {
    companion object {
        fun mapFromDomain(movie: SelectableFavoriteMovie): FavoriteEntity =
            FavoriteEntity(
                id = movie.movie.movie.id,
                movie = movie.movie.movie,
                releaseYears = movie.movie.releaseYears,
                poster = movie.movie.poster,
                rating = movie.movie.rating,
                votes = movie.movie.votes,
                genres = movie.movie.genres,
                persons = movie.movie.persons,
                videos = movie.movie.videos,
                isFavorite = movie.isFavorite
            )
    }

    fun mapToDomain(): SelectableFavoriteMovie {
        return SelectableFavoriteMovie(
            movie = MovieItem(
                movie = movie,
                releaseYears = releaseYears,
                poster = poster,
                rating = rating,
                votes = votes,
                genres = genres,
                persons = persons,
                videos = videos
            ),
            isFavorite = isFavorite
        )
    }
}

package com.dev_marinov.kinopoiskapp.presentation

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.model.Poster
import com.dev_marinov.kinopoiskapp.domain.model.Rating
import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear

object Constants {

    object DefaultMovieItem {
        val movie: Movie = defaultMovie()
        val releaseYears: List<ReleaseYear> = listOf()
        val rating: Rating = defaultRating()
        val poster: Poster = defaultPoster()
    }

    private fun defaultMovie(): Movie {
        return Movie(
            alternativeName = "",
            description = "",
            id = 0,
            length = 0,
            name = "",
            shortDescription = "",
            type = "",
            year = 0,
            page = 0
        )
    }

    private fun defaultRating(): Rating {
        return Rating(
            id = "",
            await = 0.0,
            filmCritics = 0,
            imdb = 0.0,
            kp = 0.0,
            russianFilmCritics = 0.0,
            movieId = 0
        )
    }

    private fun defaultPoster(): Poster {
        return Poster(
            id = "",
            previewUrl = "",
            url = "",
            movieId = 0
        )
    }
}
package com.dev_marinov.kinopoiskapp.domain.model.movie_combine

import com.dev_marinov.kinopoiskapp.domain.model.movie.*

data class MovieItemCombine(
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val poster: Poster?,
    val rating: Rating?,
    val votes: Votes?,
    val genres: List<Genres>,
    val persons: List<Person>,
    val videos: Videos?,
    val isFavorite: Boolean = true
)
package com.dev_marinov.kinopoiskapp.presentation.home.model

import com.dev_marinov.kinopoiskapp.domain.model.*

data class MovieItem(
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val poster: Poster?,
    val rating: Rating?,
    val votes: Votes?,
    val genres: List<Genres>,
    val persons: List<Person>,
    val videos: Videos?
)
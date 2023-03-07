package com.dev_marinov.kinopoiskapp.presentation.home.model

import com.dev_marinov.kinopoiskapp.domain.model.*

data class MovieItem(
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val rating: Rating?,
    val poster: Poster?,
    val votes: Votes?
)
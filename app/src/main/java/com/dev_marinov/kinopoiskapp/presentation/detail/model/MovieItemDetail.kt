package com.dev_marinov.kinopoiskapp.presentation.detail.model

import com.dev_marinov.kinopoiskapp.domain.model.*

data class MovieItemDetail(
    val movie: Movie,
    val poster: Poster?,
    val rating: Rating?,
    val releaseYear: ReleaseYear?,
    val vote: Votes?
)
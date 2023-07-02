package com.dev_marinov.kinopoiskapp.presentation.detail.model

import com.dev_marinov.kinopoiskapp.domain.model.*

data class MovieItemDetail(
    val movie: com.dev_marinov.kinopoiskapp.domain.model.Movie,
    val poster: com.dev_marinov.kinopoiskapp.domain.model.Poster?,
    val rating: com.dev_marinov.kinopoiskapp.domain.model.Rating?,
    val releaseYear: com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear?,
    val vote: com.dev_marinov.kinopoiskapp.domain.model.Votes?,
    val genres: List<com.dev_marinov.kinopoiskapp.domain.model.Genres>,
    val persons: List<com.dev_marinov.kinopoiskapp.domain.model.Person>,
    val videos: List<com.dev_marinov.kinopoiskapp.domain.model.Trailer>?
)
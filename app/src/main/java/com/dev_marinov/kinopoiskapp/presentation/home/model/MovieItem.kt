package com.dev_marinov.kinopoiskapp.presentation.home.model

import com.dev_marinov.kinopoiskapp.domain.model.*

data class MovieItem(
    val movie: com.dev_marinov.kinopoiskapp.domain.model.Movie,
    val releaseYears: List<com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear>,
    val poster: com.dev_marinov.kinopoiskapp.domain.model.Poster?,
    val rating: com.dev_marinov.kinopoiskapp.domain.model.Rating?,
    val votes: com.dev_marinov.kinopoiskapp.domain.model.Votes?,
    val genres: List<com.dev_marinov.kinopoiskapp.domain.model.Genres>,
    val persons: List<com.dev_marinov.kinopoiskapp.domain.model.Person>,
    val videos: com.dev_marinov.kinopoiskapp.domain.model.Videos?,
    val isFavorite: Boolean = true
)
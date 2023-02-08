package com.dev_marinov.kinopoisk.domain.model

data class ExternalId(
    val _id: String,
    val imdb: String,
    val kpHD: String?,
    val tmdb: Int
)
package com.dev_marinov.kinopoisk.domain.model

data class Rating(
    val id: String,
    val await: Double,
    val filmCritics: Int,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double,
    val movieId: Int
)
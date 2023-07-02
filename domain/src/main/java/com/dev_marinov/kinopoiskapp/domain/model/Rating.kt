package com.dev_marinov.kinopoiskapp.domain.model

data class Rating(
    val id: Int,
    val kp: Double,
    val imdb: Double,
    val filmCritics: Double,
    val russianFilmCritics: Double,
    val await: Double,
    val movieId: Int?
)
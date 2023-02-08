package com.dev_marinov.kinopoisk.domain.model

data class Rating(
    val _id: String,
    val await: Double,
    val filmCritics: Int,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double
)
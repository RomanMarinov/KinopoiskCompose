package com.dev_marinov.kinopoisk.domain.model

data class Votes(
    val id: String,
    val await: Int,
    val filmCritics: Int,
    val imdb: Int,
    val kp: Int,
    val russianFilmCritics: Int
)
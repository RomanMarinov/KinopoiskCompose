package com.dev_marinov.kinopoiskapp.domain.model

data class Votes(
    val id: Int,
    val kp: Int,
    val imdb: Int,
    val filmCritics: Int,
    val russianFilmCritics: Int,
    val await: Int,
    val movieId: Int
)
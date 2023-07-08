package com.dev_marinov.kinopoiskapp.domain.model.movie

data class ReleaseYear(
    val id: Int,
    val end: Int?,
    val start: Int,
    val movieId: Int
)
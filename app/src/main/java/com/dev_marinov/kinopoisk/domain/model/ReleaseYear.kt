package com.dev_marinov.kinopoisk.domain.model

data class ReleaseYear(
    val id: String,
    val end: Int,
    val start: Int,
    val movieId: Int
)
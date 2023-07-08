package com.dev_marinov.kinopoiskapp.domain.model.movie

data class Poster(
    val id: Int,
    val previewUrl: String,
    val url: String,
    val movieId: Int
)
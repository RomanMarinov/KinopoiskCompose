package com.dev_marinov.kinopoiskapp.domain.model

data class Videos(
    // val id: Int?,
    val trailers: List<com.dev_marinov.kinopoiskapp.domain.model.Trailer>?,
    val movieId: Int
)

package com.dev_marinov.kinopoiskapp.domain.model.movie

data class Person(
    val id: Int,
    val photo: String,
    val name: String?,
    val movieId: Int
)

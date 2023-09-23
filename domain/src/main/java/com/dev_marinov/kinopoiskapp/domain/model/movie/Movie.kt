package com.dev_marinov.kinopoiskapp.domain.model.movie

data class Movie(
    val id: Int,
    val type: String?,
    val name: String?,
    val description: String?,
    val movieLength: Int?,
    val year: Int?,
    val page: Int
)
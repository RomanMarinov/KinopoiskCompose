package com.dev_marinov.kinopoiskapp.domain.model

data class Movie(
    val alternativeName: String,
    val description: String?,
    val id: Int,
    val length: Int?,
    val name: String?,
    val shortDescription: String?,
    val type: String?,
    val year: Int?,
    val page: Int
)
package com.dev_marinov.kinopoisk.domain.model

data class Movie(
    val alternativeName: String,
    val description: String?,
    val id: Int,
    val length: Int?,
    val name: String?,
    val shortDescription: String?,
    val type: String?,
    val year: Int?,
    val releaseYearsIds: List<String>?,
    val votesId: String?,
    val ratingId: String?,
    val posterId: String?,
    val page: Int
)
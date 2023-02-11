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
    val releaseYears: List<ReleaseYear>?,
    val votes: Votes?,
    val rating: Rating?,
    val poster: Poster?,
    val page: Int
)
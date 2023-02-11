package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Movie

interface KinopoiskRepository {

    suspend fun getMovies(
        search_rating: String,
        search_date: String,
        search_typeNumber: String,
        sortType_date: String,
        sortType_rating: String
    ): List<Movie>?

}
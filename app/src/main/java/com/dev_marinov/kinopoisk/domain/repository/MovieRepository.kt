package com.dev_marinov.kinopoisk.domain.repository

interface MovieRepository {

    //    TODO: uncomment search params
    suspend fun updateMovies(
//        searchRating: String,
//        searchDate: String,
//        searchTypeNumber: String,
//        sortTypeDate: String,
//        sortTypRating: String
        page: String,
        limit: String
    )
}
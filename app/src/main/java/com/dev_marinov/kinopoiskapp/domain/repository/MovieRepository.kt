package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val movies: Flow<List<Movie>>



    //    TODO: uncomment search params
    suspend fun updateMovies(
        searchRating: String,
        searchDate: String,
        searchTypeNumber: String,
        sortTypeDate: String,
        sortTypRating: String,
        page: String,
        limit: String
    )

    suspend fun deleteMovie(movie: Movie)

    suspend fun getMovie(movieId: String?) : Movie//

}
package com.dev_marinov.kinopoisk.data.movie

import com.dev_marinov.kinopoisk.data.movie.remote.ApiService
import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getMovies(
        search_rating: String,
        search_date: String,
        search_typeNumber: String,
        sortType_date: String,
        sortType_rating: String
    ): List<Movie>? {
        val response = apiService.getData(
            search_rating = search_rating,
            search_date = search_date,
            search_typeNumber = search_typeNumber,
            sortType_date = sortType_date,
            sortType_rating = sortType_rating
        ).body()

        return response?.movies?.map {
            it.mapToDomain(response.page)
        }
    }
}
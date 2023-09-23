package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MovieRepository {

    val movies: Flow<List<Movie>>
    val countMovies: Flow<Int>
    val countSelectGenre: Flow<Int>

    val countStatAll: Flow<Int>
    val countStatMovies: Flow<Int>
    val countStatTvSeries: Flow<Int>
    val countStatCartoon: Flow<Int>
    val countStatAnime: Flow<Int>
    val countStatAnimatedSeries: Flow<Int>

    val responseCodeAllMovies: MutableStateFlow<Int>

    suspend fun updateMovies(
        searchDate: String,
        searchRating: String,
        searchType: String,
        page: Int
    )

//    suspend fun deleteMovie(movieNew: Movie)

    suspend fun getMovie(movieId: String?): Movie

    suspend fun sortByGenres(genre: String)

    suspend fun hasGenreDataForBottomSheet(selectGenres: String): Flow<Int>
    suspend fun hasGenreDataForBottomNavigationBar(selectGenres: String)
    suspend fun clearAllMovies()
}
package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val movies: Flow<List<Movie>>
   // suspend fun getMoviesMethod()

    suspend fun updateMovies(
        searchDate: String,
        searchRating: String,
        searchType: String,
       // sortTypRating: String,
        //page: String,
       // limit: String
    )

    suspend fun deleteMovie(movieNew: Movie)
    suspend fun getMovie(movieId: String?) : Movie//

    val countMovies : Flow<Int>

    val countSelectGenre : Flow<Int>

//    suspend fun sortByGenres(genre: String)
    suspend fun sortByGenres(genre: String)
    suspend fun hasGenreDataForBottomSheet(selectGenres: String): Flow<Int>
    suspend fun hasGenreDataForBottomNavigationBar(selectGenres: String)

}
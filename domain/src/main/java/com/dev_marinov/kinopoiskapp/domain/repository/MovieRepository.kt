package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val movies: Flow<List<Movie>>
    val countMovies: Flow<Int>
    val countSelectGenre: Flow<Int>
    val isPlayingLottie: Flow<Boolean>

    val countStatAll: Flow<Int>
    val countStatMovies: Flow<Int>
    val countStatTvSeries: Flow<Int>
    val countStatCartoon: Flow<Int>
    val countStatAnime: Flow<Int>
    val countStatAnimatedSeries: Flow<Int>

    suspend fun updateMovies(
        searchDate: String,
        searchRating: String,
        searchType: String,
        page: Int
    )

    suspend fun deleteMovie(movieNew: Movie)

    suspend fun getMovie(movieId: String?): Movie

    suspend fun sortByGenres(genre: String)

    suspend fun hasGenreDataForBottomSheet(selectGenres: String): Flow<Int>
    suspend fun hasGenreDataForBottomNavigationBar(selectGenres: String)
    suspend fun clearAllMovies()

    suspend fun playingLottieAnimation(isPlaying: Boolean)
}
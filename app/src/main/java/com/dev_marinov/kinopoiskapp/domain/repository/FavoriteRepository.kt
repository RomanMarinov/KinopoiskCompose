package com.dev_marinov.kinopoiskapp.domain.repository

import androidx.lifecycle.LiveData
import com.dev_marinov.kinopoiskapp.presentation.model.SelectableFavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {


    val favoriteMoviesForHome: Flow<List<SelectableFavoriteMovie>>
    val favoriteMoviesForDetail: LiveData<List<SelectableFavoriteMovie>>

   // suspend fun toggleFavoriteMovieStatus(selectableFavoriteMovie: SelectableFavoriteMovie)

    //////
    suspend fun deleteFavoriteMovie(movie: SelectableFavoriteMovie)
    suspend fun saveFavoriteMovie(movie: SelectableFavoriteMovie)
    suspend fun clearAllMovies()

    val countFavorite: Flow<Int>
}
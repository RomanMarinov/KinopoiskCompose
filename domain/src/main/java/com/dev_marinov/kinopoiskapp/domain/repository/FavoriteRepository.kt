package com.dev_marinov.kinopoiskapp.domain.repository

import androidx.lifecycle.LiveData
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    val favoriteMoviesForHome: Flow<List<SelectableFavoriteMovie>>
    val favoriteMoviesForDetail: LiveData<List<SelectableFavoriteMovie>>
    val countFavorite: Flow<Int>

    suspend fun deleteFavoriteMovie(movie: SelectableFavoriteMovie)
    suspend fun saveFavoriteMovie(movie: SelectableFavoriteMovie)
    suspend fun clearAllMovies()
}
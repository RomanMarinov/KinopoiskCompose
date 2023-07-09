package com.dev_marinov.kinopoiskapp.domain.usecase

import androidx.lifecycle.LiveData
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import com.dev_marinov.kinopoiskapp.domain.util.UseCaseFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : UseCaseFavorite<GetFavoriteUseCase.GetFavoriteMovieParams>(Dispatchers.IO) {

    data class GetFavoriteMovieParams(val movie: SelectableFavoriteMovie)

    override suspend fun save(parameters: GetFavoriteMovieParams) {
        favoriteRepository.saveFavoriteMovie(movie = parameters.movie)
    }

    override suspend fun delete(parameters: GetFavoriteMovieParams) {
        favoriteRepository.deleteFavoriteMovie(movie = parameters.movie)
    }

    override suspend fun clear() {
        favoriteRepository.clearAllMovies()
    }

    fun getFavoriteMoviesForHomeFlow(): Flow<List<SelectableFavoriteMovie>> {
        return favoriteRepository.favoriteMoviesForHome
    }

    fun getFavoriteMoviesForDetailFlow(): LiveData<List<SelectableFavoriteMovie>> {
        return favoriteRepository.favoriteMoviesForDetail
    }

    fun getCountFavoriteFlow(): Flow<Int> {
        return favoriteRepository.countFavorite
    }
}
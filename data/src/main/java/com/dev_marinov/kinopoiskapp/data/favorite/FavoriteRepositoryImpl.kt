package com.dev_marinov.kinopoiskapp.data.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository
import com.dev_marinov.kinopoiskapp.presentation.model.SelectableFavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao) :
    FavoriteRepository {

    private val _favoriteMovies = MediatorLiveData<List<SelectableFavoriteMovie>>()

    init {
        val source = favoriteDao.getFavorites()
            .map { movies ->
                movies.map { it.mapToDomain() }
            }
        _favoriteMovies.addSource(source) { movies ->
            _favoriteMovies.value = movies.reversed()
        }
    }

    override val favoriteMoviesForHome: Flow<List<SelectableFavoriteMovie>> =
        _favoriteMovies.asFlow()

    override val favoriteMoviesForDetail: LiveData<List<SelectableFavoriteMovie>>
        get() = _favoriteMovies

    override val countFavorite: Flow<Int> = favoriteDao.getCountFavorite()

    override suspend fun deleteFavoriteMovie(movie: SelectableFavoriteMovie) {
        val favoriteMovieEntity = FavoriteEntity.mapFromDomain(movie = movie)
        favoriteDao.delete(favoriteMovieEntity)
    }

    override suspend fun saveFavoriteMovie(movie: SelectableFavoriteMovie) {
        val favoriteMovieEntity = FavoriteEntity.mapFromDomain(movie = movie)
        favoriteDao.insert(favoriteMovieEntity)
    }

    override suspend fun clearAllMovies() {
        favoriteDao.clearAllMovies()
    }
}
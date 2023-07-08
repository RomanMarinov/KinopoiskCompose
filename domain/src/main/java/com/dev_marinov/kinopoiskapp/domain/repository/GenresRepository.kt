package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.Genres
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    val genres: Flow<List<Genres>>
    suspend fun getGenres(movieId: Int): List<Genres>
}

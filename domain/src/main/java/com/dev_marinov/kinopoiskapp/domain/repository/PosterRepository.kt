package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface PosterRepository {

    val posters: Flow<List<Poster>>

    suspend fun getPoster(movieId: Int): Poster?
}
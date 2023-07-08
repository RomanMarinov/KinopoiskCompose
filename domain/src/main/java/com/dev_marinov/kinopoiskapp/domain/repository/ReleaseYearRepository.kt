package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.ReleaseYear
import kotlinx.coroutines.flow.Flow

interface ReleaseYearRepository {

    val releaseYears: Flow<List<ReleaseYear>>
    // val releaseYear: Flow<ReleaseYear>

    suspend fun getReleaseYear(movieId: Int): ReleaseYear?
}
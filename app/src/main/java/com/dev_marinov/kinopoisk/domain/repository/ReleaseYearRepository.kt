package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import kotlinx.coroutines.flow.Flow

interface ReleaseYearRepository {

    val releaseYears: Flow<List<ReleaseYear>>
}
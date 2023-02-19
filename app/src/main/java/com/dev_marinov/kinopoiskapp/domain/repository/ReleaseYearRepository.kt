package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear
import kotlinx.coroutines.flow.Flow

interface ReleaseYearRepository {

    val releaseYears: Flow<List<ReleaseYear>>
}
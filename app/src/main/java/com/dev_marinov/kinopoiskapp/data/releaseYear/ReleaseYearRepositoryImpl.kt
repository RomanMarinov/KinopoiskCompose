package com.dev_marinov.kinopoiskapp.data.releaseYear

import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearDao
import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear
import com.dev_marinov.kinopoiskapp.domain.repository.ReleaseYearRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReleaseYearRepositoryImpl @Inject constructor(
    localDataSource: ReleaseYearDao
) : ReleaseYearRepository {

    override val releaseYears: Flow<List<ReleaseYear>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }
}
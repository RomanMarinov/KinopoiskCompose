package com.dev_marinov.kinopoisk.data.rating

import com.dev_marinov.kinopoisk.data.rating.local.RatingDao
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.repository.RatingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(localDataSource: RatingDao) : RatingRepository {

    override val ratings: Flow<List<Rating>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }
}
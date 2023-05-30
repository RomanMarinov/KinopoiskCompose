package com.dev_marinov.kinopoiskapp.data.rating

import com.dev_marinov.kinopoiskapp.data.rating.local.RatingDao
import com.dev_marinov.kinopoiskapp.domain.model.Rating
import com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(private val localDataSource: RatingDao) : RatingRepository {

    override val ratings: Flow<List<Rating>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    override suspend fun getRating(movieId: Int): Rating? {
        return localDataSource.getRatingsForDetail(movieId)?.mapToDomain()
    }

//    override val sortingRating: Flow<List<Rating>> = localDataSource.sortingASCKp().map {
//        it.map { ratingEntity -> ratingEntity.mapToDomain() }
//    }


}
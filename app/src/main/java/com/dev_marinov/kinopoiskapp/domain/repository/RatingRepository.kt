package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Rating
import kotlinx.coroutines.flow.Flow

interface RatingRepository {

    val ratings: Flow<List<Rating>>
   suspend fun getRating(movieId: Int): Rating?
   // val sortingRating: Flow<List<Rating>>
}
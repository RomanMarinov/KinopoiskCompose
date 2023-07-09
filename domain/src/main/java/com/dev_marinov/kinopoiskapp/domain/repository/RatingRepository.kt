package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.Rating
import kotlinx.coroutines.flow.Flow

interface RatingRepository {

    val ratings: Flow<List<Rating>>
    suspend fun getRating(movieId: Int): Rating
}
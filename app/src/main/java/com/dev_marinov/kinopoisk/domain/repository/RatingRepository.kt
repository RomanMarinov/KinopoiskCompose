package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Rating
import kotlinx.coroutines.flow.Flow

interface RatingRepository {

    val ratings: Flow<List<Rating>>
}
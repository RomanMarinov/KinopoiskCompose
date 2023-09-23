package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Rating
import com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatingUseCase @Inject constructor(private val ratingRepository: RatingRepository) :
    UseCase<GetRatingUseCase.GetRatingParams, Rating>(Dispatchers.IO) {

    data class GetRatingParams(val movieId: Int)

    override suspend fun execute(parameters: GetRatingParams): Rating {
        return ratingRepository.getRating(movieId = parameters.movieId)
    }

    fun getRatingsFlow(): Flow<List<Rating>> {
        return ratingRepository.ratings
    }
}
package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.ReleaseYear
import com.dev_marinov.kinopoiskapp.domain.repository.ReleaseYearRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReleaseYearUseCase @Inject constructor(private val releaseYearRepository: ReleaseYearRepository) :
    UseCase<GetReleaseYearUseCase.GetReleaseYearParams, ReleaseYear>(Dispatchers.IO) {

    data class GetReleaseYearParams(val movieId: Int)

    override suspend fun execute(parameters: GetReleaseYearParams): ReleaseYear {
        return releaseYearRepository.getReleaseYear(movieId = parameters.movieId)
    }

    fun getReleaseYearFlow(): Flow<List<ReleaseYear>> {
        return releaseYearRepository.releaseYears
    }
}
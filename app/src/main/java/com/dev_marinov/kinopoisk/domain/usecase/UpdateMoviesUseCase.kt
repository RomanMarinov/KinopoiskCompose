package com.dev_marinov.kinopoisk.domain.usecase

import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import com.dev_marinov.kinopoisk.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<UpdateMoviesUseCase.UpdateMoviesParams, Unit>(Dispatchers.IO) {

    override suspend fun execute(parameters: UpdateMoviesParams) {
        movieRepository.updateMovies(parameters.page, parameters.limit)
    }

    class UpdateMoviesParams(
        val page: String,
        val limit: String
    )
}
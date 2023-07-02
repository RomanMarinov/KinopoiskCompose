package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import javax.inject.Inject

/**
 * UseCase for removing [Movie] from database
 */
class DeleteMovieUseCase @Inject constructor(
    private val movieRepository: com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
) : UseCase<com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase.DeleteMovieParams, Unit>() {

    override suspend fun execute(parameters: _root_ide_package_.com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase.DeleteMovieParams) {
        movieRepository.deleteMovie(parameters.movie)
    }

    data class DeleteMovieParams(val movie: com.dev_marinov.kinopoiskapp.domain.model.Movie)
}
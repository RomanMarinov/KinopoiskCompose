package com.dev_marinov.kinopoisk.domain.usecase

import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import com.dev_marinov.kinopoisk.domain.util.UseCase
import javax.inject.Inject

/**
 * UseCase for removing [Movie] from database
 */
class DeleteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<DeleteMovieUseCase.DeleteMovieParams, Unit>() {

    override suspend fun execute(parameters: DeleteMovieParams) {
        movieRepository.deleteMovie(parameters.movie)
    }

    data class DeleteMovieParams(val movie: Movie)
}
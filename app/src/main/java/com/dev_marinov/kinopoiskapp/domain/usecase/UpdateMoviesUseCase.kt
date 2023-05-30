package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<UpdateMoviesUseCase.UpdateMoviesParams, Unit>(Dispatchers.IO) {


    override suspend fun execute(parameters: UpdateMoviesParams) {
        movieRepository.updateMovies(parameters.fromToYears, parameters.fromToRatings, parameters.genre)
//        movieRepository.updateMovies(parameters.page, parameters.limit)
    }

    class UpdateMoviesParams(
        val fromToYears: String,
        val fromToRatings: String,
        val genre: String
    )

    // было до передачи параметров из боттом щит
//    override suspend fun execute(parameters: UpdateMoviesParams) {
//        movieRepository.updateMovies("", "", "", "","","", "")
////        movieRepository.updateMovies(parameters.page, parameters.limit)
//    }
//
//    class UpdateMoviesParams(
//        val page: String,
//        val limit: String
//    )
}
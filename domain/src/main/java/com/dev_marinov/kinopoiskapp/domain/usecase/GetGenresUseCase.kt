package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Genres
import com.dev_marinov.kinopoiskapp.domain.repository.GenresRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val genresRepository: GenresRepository) :
    UseCase<GetGenresUseCase.GetGenresParams, List<Genres>>(Dispatchers.IO) {

    data class GetGenresParams(val movieId: Int)

    override suspend fun execute(parameters: GetGenresParams): List<Genres> {
        return genresRepository.getGenres(movieId = parameters.movieId)
    }

    fun getGenresFlow(): Flow<List<Genres>> {
        return genresRepository.genres
    }
}
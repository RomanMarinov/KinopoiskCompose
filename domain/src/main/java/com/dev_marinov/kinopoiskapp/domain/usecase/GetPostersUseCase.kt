package com.dev_marinov.kinopoiskapp.domain.usecase

import androidx.compose.ui.modifier.modifierLocalMapOf
import com.dev_marinov.kinopoiskapp.domain.model.movie.Poster
import com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(private val posterRepository: PosterRepository) :
    UseCase<GetPostersUseCase.GetPostersParams, Poster>(Dispatchers.IO) {

    data class GetPostersParams(val movieId: Int)

    override suspend fun execute(parameters: GetPostersParams): Poster {
        return posterRepository.getPoster(movieId = parameters.movieId)
    }

    fun getPostersFlow(): Flow<List<Poster>> {
        return posterRepository.posters
    }
}
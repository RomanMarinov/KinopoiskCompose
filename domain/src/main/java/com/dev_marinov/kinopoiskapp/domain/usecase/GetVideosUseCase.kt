package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.movie.Videos
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val videosRepository: VideosRepository) :
    UseCase<GetVideosUseCase.GetVideosParams, List<Trailer>>(Dispatchers.IO) {

    data class GetVideosParams(val movieId: Int)

    override suspend fun execute(parameters: GetVideosParams): List<Trailer> {
        return videosRepository.getTrailersForDetail(movieId = parameters.movieId)
    }

    fun getVideosFlow(): Flow<List<Videos>> {
        return videosRepository.videos
    }
}
package com.dev_marinov.kinopoiskapp.data.video

import com.dev_marinov.kinopoiskapp.data.video.local.VideosDao
import com.dev_marinov.kinopoiskapp.domain.model.movie.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.movie.Videos
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepositoryImpl @Inject constructor(private val videosDao: VideosDao) : VideosRepository {

    override val videos: Flow<List<Videos>> = videosDao.getAllFlow().map {

        it.map { videosEntity ->
            videosEntity.mapToDomain()
        }
    }

    override suspend fun getTrailersForDetail(movieId: Int): List<Trailer> {
        return videosDao.getTrailers(movie_id = movieId).trailers
    }
}
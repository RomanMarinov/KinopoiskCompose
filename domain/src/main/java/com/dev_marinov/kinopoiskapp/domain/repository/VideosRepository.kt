package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.movie.Videos
import kotlinx.coroutines.flow.Flow

interface VideosRepository {

    val videos: Flow<List<Videos>>
    suspend fun getTrailersForDetail(movieId: Int): List<Trailer>
}
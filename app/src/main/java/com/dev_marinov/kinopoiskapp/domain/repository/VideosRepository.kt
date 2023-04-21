package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.Videos
import kotlinx.coroutines.flow.Flow

interface VideosRepository {

    val videos: Flow<List<Videos>>
    suspend fun getTrailersForDetail(movieId: Int) : List<Trailer>?
}
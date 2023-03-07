package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface PosterRepository {

    val posters: Flow<List<Poster>>
    //val poster: Flow<Poster>

    suspend fun getPoster(movieId: Int) : Poster?
   // suspend fun getPostersForDetail() : List<Poster>

}
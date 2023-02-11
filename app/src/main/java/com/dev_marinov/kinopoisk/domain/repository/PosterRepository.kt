package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface PosterRepository {

    val posters: Flow<List<Poster>>
}
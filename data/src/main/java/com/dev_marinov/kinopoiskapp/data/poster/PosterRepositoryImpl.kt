package com.dev_marinov.kinopoiskapp.data.poster

import com.dev_marinov.kinopoiskapp.data.poster.local.PosterDao
import com.dev_marinov.kinopoiskapp.domain.model.Poster
import com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PosterRepositoryImpl @Inject constructor(private val localDataSource: PosterDao) : PosterRepository {

    override val posters: Flow<List<Poster>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    override suspend fun getPoster(movieId: Int): Poster? {
        return localDataSource.getPosterForDetail(movieId)?.mapToDomain()
    }
}
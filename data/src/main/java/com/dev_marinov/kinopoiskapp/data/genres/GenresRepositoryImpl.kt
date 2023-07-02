package com.dev_marinov.kinopoiskapp.data.genres

import com.dev_marinov.kinopoiskapp.data.genres.local.GenresDao
import com.dev_marinov.kinopoiskapp.domain.model.Genres
import com.dev_marinov.kinopoiskapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenresRepositoryImpl
@Inject constructor(private val localDataSource: GenresDao) : GenresRepository {
    override val genres: Flow<List<Genres>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    override suspend fun getGenres(movieId: Int): List<Genres> {
        return localDataSource.getGenresForDetail(movie_id = movieId).map {
            it.mapToDomain()
        }
    }
}
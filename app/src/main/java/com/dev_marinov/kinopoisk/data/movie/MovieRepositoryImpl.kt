package com.dev_marinov.kinopoisk.data.movie

import com.dev_marinov.kinopoisk.data.movie.local.MovieDao
import com.dev_marinov.kinopoisk.data.movie.remote.ApiService
import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.repository.RepositoryMediator
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: MovieDao,
    private val mediator: RepositoryMediator
) : MovieRepository {

    override val movies: Flow<List<Movie>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    //    TODO: uncomment search params
    override suspend fun updateMovies(
//        searchRating: String,
//        searchDate: String,
//        searchTypeNumber: String,
//        sortTypeDate: String,
//        sortTypRating: String
        page: String,
        limit: String
    ) {
        // если данных по сети нет
        val response = remoteDataSource.getData(page, limit).body() ?: return
        val movieDtos = response.movies
        movieDtos.forEach { dto ->
            val movie = dto.mapToDomain(response.page)
            val releaseYears = dto.releaseYears?.map { it.mapToDomain(movie.id) } ?: listOf()
            val votes = dto.votes?.mapToDomain(movie.id)
            val rating = dto.rating?.mapToDomain(movie.id)
            val poster = dto.poster?.mapToDomain(movie.id)
            mediator.saveData(movie, releaseYears, votes, rating, poster)
        }
    }
}
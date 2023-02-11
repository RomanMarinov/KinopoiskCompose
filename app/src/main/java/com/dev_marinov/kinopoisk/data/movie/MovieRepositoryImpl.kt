package com.dev_marinov.kinopoisk.data.movie

import com.dev_marinov.kinopoisk.data.movie.local.MovieDao
import com.dev_marinov.kinopoisk.data.movie.remote.ApiService
import com.dev_marinov.kinopoisk.domain.repository.RepositoryMediator
import com.dev_marinov.kinopoisk.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: MovieDao,
    private val mediator: RepositoryMediator
) : MovieRepository {

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
        val response = remoteDataSource.getData(page, limit).body() ?: return
        val movieDtos = response.movies
        movieDtos.forEach { dto ->
            val movie = dto.mapToDomain(response.page)
            val releaseYears = dto.releaseYears?.map { it.mapToDomain() } ?: listOf()
            val votes = dto.votes?.mapToDomain()
            val rating = dto.rating?.mapToDomain()
            val poster = dto.poster?.mapToDomain()
            mediator.saveData(movie, releaseYears, votes, rating, poster)
        }
    }
}
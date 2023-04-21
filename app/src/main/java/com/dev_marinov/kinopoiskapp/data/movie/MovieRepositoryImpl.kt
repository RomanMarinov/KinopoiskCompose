package com.dev_marinov.kinopoiskapp.data.movie

import android.util.Log
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.movie.remote.ApiService
import com.dev_marinov.kinopoiskapp.data.video.remote.VideosDTO
import com.dev_marinov.kinopoiskapp.domain.model.Genre
import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.model.Videos
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.repository.RepositoryCoordinator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: MovieDao,
    val repositoryCoordinator: RepositoryCoordinator
) : MovieRepository {

    override val movies: Flow<List<Movie>> = localDataSource.getAllFlow().map {
        it.map { entity ->
            entity.mapToDomain()
        }
    }

    override val countMovies: Flow<Int> = localDataSource.getRowCount()

    //    TODO: uncomment search params
    override suspend fun updateMovies(
        searchRating: String,
        searchDate: String,
        searchTypeNumber: String,
        sortTypeDate: String,
        sortTypRating: String,
        page: String,
        limit: String,
    ) {
        // Log.d("4444", " MovieRepositoryImplNew response")

        // если данных по сети нет
        val response = remoteDataSource.getData().body() ?: return // TODO: uncomment this line


        val movieDtos = response.movies //  uncomment this line
        // val movieDtos = getData() //  delete this line
        //Log.d("4444", " MovieRepositoryImplNew movieDtos=" + movieDtos)
        movieDtos.forEach { dto ->

            try {
                // Log.d("4444", " MovieRepositoryImplNew count")
                val movie = dto.mapToDomain(response.page) //  uncomment this line
                // val movie = dto.mapToDomain(1) //  delete this line
                val releaseYears = dto.releaseYears?.map {
                    it.mapToDomain(movieId = movie.id)
                } ?: listOf()
                val votes = dto.votes?.mapToDomain(movieId = movie.id)
                val rating = dto.rating?.mapToDomain(movieId = movie.id)
                val poster = dto.poster?.mapToDomain(movieId = movie.id)
                val persons = dto.persons?.map {
                    it.mapToDomain(movieId = movie.id)
                }
                // чтобы получать данные, должно быть только GenreDTO
                val genres = dto.genres?.map {
                    it.mapToDomain(movieId = movie.id)
                }

               // val videos = dto.videos.trailers.map { it.mapToDomain(movieId = movie.id) }
                val videos = dto.videos.mapToDomain(movieId = movie.id)


               // val videos = dto.videos.mapToDomain(movieId = movie.id)
              //  Log.d("4444", " MovieRepositoryImplNew videos=" + videos)

                repositoryCoordinator.saveData(
                    movie,
                    releaseYears,
                    votes,
                    rating,
                    poster,
                    genres,
                    persons,
                    videos
                )
            } catch (e: Exception) {
                Log.d("4444", " MovieRepositoryImplNew try catch e=" + e)
            }
        }
    }

    override suspend fun getMovie(movieId: String?): Movie {
        return localDataSource.getMovieForDetail(id = movieId).mapToDomain()
    }


//    override fun getCountMovies(): Flow<Int> {
//        return localDataSource.getRowCount()
//    }

    override suspend fun deleteMovie(movieNew: Movie) {
        localDataSource.delete(MovieEntity.mapFromDomain(movieNew))
    }
}
package com.dev_marinov.kinopoiskapp.data.movie

import android.util.Log
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.movie.remote.ApiService
import com.dev_marinov.kinopoiskapp.data.movie.remote.GetMoviesResponse
import com.dev_marinov.kinopoiskapp.domain.model.movie.Movie
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.repository.RepositoryCoordinator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: MovieDao,
    val repositoryCoordinator: RepositoryCoordinator,
) : MovieRepository {

    override val countStatAll: Flow<Int> = localDataSource.getCountStatAll()
    override val countStatMovies: Flow<Int> = localDataSource.getCountStatMovie("movie")
    override val countStatTvSeries: Flow<Int> = localDataSource.getCountStatTvSeries("tv-series")
    override val countStatCartoon: Flow<Int> = localDataSource.getCountStatCartoon("cartoon")
    override val countStatAnime: Flow<Int> = localDataSource.getCountStatAnime("anime")
    override val countStatAnimatedSeries: Flow<Int> = localDataSource.getCountStatAnimatedSeries("animated-series")
    override val responseCodeAllMovies: MutableStateFlow<Int> = MutableStateFlow(0)

    override val countSelectGenre: MutableStateFlow<Int> = MutableStateFlow(0)

    private val filteredMoviesFlow: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())

    // проверить возможно комбайн не нужен и не нужен movies
    override var movies: Flow<List<Movie>> = localDataSource.getAllFlow()
        .map { entityList ->
            entityList.map { entity ->
                (entity).mapToDomain() // явное приведение к типу MovieEntity
            }
        }
        .combine(filteredMoviesFlow) { allMovies, filteredMovies ->
            if (filteredMovies.isEmpty()) {
                allMovies
            } else {
                filteredMovies
            }
        }

    override val countMovies: Flow<Int> = localDataSource.getRowCount()

    override suspend fun sortByGenres(genre: String) {
        if (genre == "all") {
            localDataSource.getAllFlow().collect { entityList ->
                val movieList = entityList.map { it.mapToDomain() }
                filteredMoviesFlow.value = movieList
            }
        } else {
            localDataSource.sortingGenre(genre = genre).collect { entityList ->
                val movieList = entityList.map { it.mapToDomain() }
                filteredMoviesFlow.value = movieList
            }
        }
    }

    override suspend fun hasGenreDataForBottomSheet(selectGenres: String): Flow<Int> {
        return if (selectGenres.lowercase() == "all") {
            localDataSource.getRowCount()
        } else {
            localDataSource.hasGenreData(selectGenres = selectGenres)
        }
    }

    override suspend fun hasGenreDataForBottomNavigationBar(selectGenres: String) {
        if (selectGenres.lowercase() == "all") {
            localDataSource.getRowCount().collect {
                countSelectGenre.value = it
            }
        } else {
            localDataSource.hasGenreData(selectGenres = selectGenres).collect {
                countSelectGenre.value = it
            }
        }
    }

    override suspend fun clearAllMovies() {
        localDataSource.clearAllMovies()
    }

    override suspend fun updateMovies(
        searchDate: String,
        searchRating: String,
        searchType: String,
        page: Int
    ) {
        val response = remoteDataSource.getData(
            selectFields = listOf(
                "poster", "rating", "genres", "externalId", "movieLength",
                "persons", "videos.trailers", "votes", "id", "type", "description", "year",
                "releaseYears", "name"
            ),
            type = searchType,
            field = listOf("rating.kp", "year"),
            search = listOf(searchRating, searchDate),
            videosTrailers = "!null",
            page = page,
            limit = 20
        )

        when(response.code()) {
            200 -> {
                Log.d("4444", " response=" + response)

                response.body()?.let {
                    val movieDtos = it.movies //  uncomment this line
                    movieDtos.forEach { dto ->

                        try {
                            val movie = dto.mapToDomain(it.page) //  uncomment this line
                            // val movie = dto.mapToDomain(1) //  delete this line
                            val releaseYears = dto.releaseYears?.map { releaseYearDTO ->
                                releaseYearDTO.mapToDomain(movieId = movie.id)
                            } ?: listOf()
                            val votes = dto.votes?.mapToDomain(movieId = movie.id)
                            val rating = dto.rating?.mapToDomain(movieId = movie.id)
                            val poster = dto.poster?.mapToDomain(movieId = movie.id)
                            val persons = dto.persons?.map { personsDTO ->
                                personsDTO.mapToDomain(movieId = movie.id)
                            }

                            val genres = dto.genres?.map { genreDTO ->
                                genreDTO.mapToDomain(movieId = movie.id)
                            }

                            val videos = dto.videos.mapToDomain(movieId = movie.id)

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
            }
            404 -> {

            }
            403 -> {
                responseCodeAllMovies.value = response.code()
            }
        }
    }

    override suspend fun getMovie(movieId: String?): Movie {
        return localDataSource.getMovieForDetail(id = movieId).mapToDomain()
    }
}
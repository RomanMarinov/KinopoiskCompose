package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Movie
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<GetMovieUseCase.UpdateMoviesParams, Unit>(Dispatchers.IO) {

    override suspend fun execute(parameters: UpdateMoviesParams) {
        movieRepository.updateMovies(
            parameters.fromToYears,
            parameters.fromToRatings,
            parameters.genre,
            parameters.page
        )
    }

    class UpdateMoviesParams(
        val fromToYears: String,
        val fromToRatings: String,
        val genre: String,
        val page: Int
    )

    /////////////
    val movies: Flow<List<Movie>> = movieRepository.movies
    val countMovies: Flow<Int> = movieRepository.countMovies
    val countSelectGenre: Flow<Int> = movieRepository.countSelectGenre

    val responseCodeAllMovies: MutableStateFlow<Int> = movieRepository.responseCodeAllMovies

    val countStatAll: Flow<Int> = movieRepository.countStatAll
    val countStatMovies: Flow<Int> = movieRepository.countStatMovies
    val countStatTvSeries: Flow<Int> = movieRepository.countStatTvSeries
    val countStatCartoon: Flow<Int> = movieRepository.countStatCartoon
    val countStatAnime: Flow<Int> = movieRepository.countStatAnime
    val countStatAnimatedSeries: Flow<Int> = movieRepository.countStatAnimatedSeries

    suspend fun getMovie(movieId: String?): Movie {
        return withContext(dispatcher) {
            movieRepository.getMovie(movieId = movieId)
        }
    }

    suspend fun getSortByGenres(genre: String) {
        movieRepository.sortByGenres(genre = genre)
    }

    suspend fun getHasGenreDataForBottomSheet(selectGenres: String): Flow<Int> {
        return movieRepository.hasGenreDataForBottomSheet(selectGenres = selectGenres)
    }

    suspend fun getHasGenreDataForBottomNavigationBar(selectGenres: String) {
        movieRepository.hasGenreDataForBottomNavigationBar(selectGenres = selectGenres)
    }

    suspend fun getClearAllMovies() {
        movieRepository.clearAllMovies()
    }
}
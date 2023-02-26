package com.dev_marinov.kinopoiskapp.presentation.detail

import androidx.lifecycle.ViewModel
import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.model.Poster
import com.dev_marinov.kinopoiskapp.domain.model.Rating
import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository
import com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository
import com.dev_marinov.kinopoiskapp.domain.repository.ReleaseYearRepository
import com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val movieRepository: MovieRepository,
    private val posterRepository: PosterRepository,
    private val releaseYearRepository: ReleaseYearRepository,
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val page = 1

    private val movies: Flow<List<Movie>> = getMovies()
    private val moviesIds: Flow<Set<Int>> = movies.map { it.map { movie -> movie.id }.toSet() }
    private val posters: Flow<List<Poster>> = getPosters()
    private val releaseYears: Flow<List<ReleaseYear>> = getReleaseYears()
    private val ratings: Flow<List<Rating>> = getRatings()

    //    Items which we should show on screen
    val movieItems: Flow<List<MovieItem>> = getItems()

    init {

    }


    private fun getMovies(): Flow<List<Movie>> = movieRepository.movies.map { movies ->
        movies.filter { movie -> movie.page == page }
    }

    private fun getPosters(): Flow<List<Poster>> {
        return posterRepository.posters.combine(moviesIds) { posters, ids ->
            posters.filter { it.movieId in ids }
        }
    }

    private fun getReleaseYears(): Flow<List<ReleaseYear>> {
        return releaseYearRepository.releaseYears.combine(moviesIds) { years, ids ->
            years.filter { it.movieId in ids }
        }
    }

    private fun getRatings(): Flow<List<Rating>> {
        return ratingRepository.ratings.combine(moviesIds) { ratings, ids ->
            ratings.filter { it.movieId in ids }
        }
    }

    private fun getItems(): Flow<List<MovieItem>> {
        return combine(movies, posters, releaseYears, ratings) { movies, posters, years, ratings ->
            movies.map { movie ->
                MovieItem(
                    movie = movie,
                    releaseYears = years.filter { it.movieId == movie.id },
                    rating = ratings.firstOrNull { it.movieId == movie.id },
                    poster = posters.firstOrNull { it.movieId == movie.id }
                )
            }
        }
    }
}
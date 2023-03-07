package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val movieRepository: MovieRepository,
    private val posterRepository: PosterRepository,
    private val releaseYearRepository: ReleaseYearRepository,
    private val ratingRepository: RatingRepository,
    private val votesRepository: VotesRepository
) : ViewModel() {

    private val page = 1

    private val movies: Flow<List<Movie>> = getMovies()
    private val moviesIds: Flow<Set<Int>> = movies.map { it.map { movie -> movie.id }.toSet() }
    private val posters: Flow<List<Poster>> = getPosters()
    private val releaseYears: Flow<List<ReleaseYear>> = getReleaseYears()
    private val ratings: Flow<List<Rating>> = getRatings()
    private val votes: Flow<List<Votes>> = getVotes()

    //    Items which we should show on screen
    val movieItems: Flow<List<MovieItem>> = getItems()

    init {


        getData()
    }

    /**
     * When we click on [Movie], we delete it from database.
     * This should cause the removal of elements associated with this movie: [Rating], [ReleaseYear], [Poster] and [Votes].
     * Removal is launched through a [DeleteMovieUseCase]
     */
    fun onMovieClicked(movieItem: MovieItem) {
        viewModelScope.launch {
            deleteMovieUseCase(DeleteMovieUseCase.DeleteMovieParams(movieItem.movie))
        }
    }

    private fun getData() {
        viewModelScope.launch {
            updateMoviesUseCase.invoke(
                UpdateMoviesUseCase.UpdateMoviesParams("", "")
//                UpdateMoviesUseCase.UpdateMoviesParams(page.toString(), "20")
            )
        }
    }

//    TODO: maybe delete or edit
//    private fun getData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = movieRepository.updateMovies("7-10", "2017-2020", "2", "1", "-1")
//            Log.d("4444", " ListViewModel response=" + response)
//
//
////            response?.let {
////                _viewState.value = it
////            }
//        }
//    }

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
            years.filter {
                it.movieId in ids
            }
        }
    }

    private fun getRatings(): Flow<List<Rating>> {
        return ratingRepository.ratings.combine(moviesIds) { ratings, ids ->
            ratings.filter { it.movieId in ids }
        }
    }

    private fun getVotes(): Flow<List<Votes>> {
        return votesRepository.votes.combine(moviesIds) { votes, ids ->
            votes.filter { it.movieId in ids }
        }
    }

    private fun getItems(): Flow<List<MovieItem>> {
        return combine(movies, posters, releaseYears, ratings, votes) { movies, posters, years, ratings, votes ->
            movies.map { movie ->
                MovieItem(
                    movie = movie,
                    releaseYears = years.filter {
                        it.movieId == movie.id
                    },
                    rating = ratings.firstOrNull {
                        it.movieId == movie.id
                    },
                    poster = posters.firstOrNull {
                        it.movieId == movie.id
                    },
                    votes = votes.firstOrNull {
                        it.movieId == movie.id
                    }
                )
            }
        }
    }
}
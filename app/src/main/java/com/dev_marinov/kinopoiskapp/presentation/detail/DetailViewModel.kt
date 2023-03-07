package com.dev_marinov.kinopoiskapp.presentation.detail

import androidx.lifecycle.*
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import com.dev_marinov.kinopoiskapp.presentation.detail.model.MovieItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val movieRepository: MovieRepository,
    private val posterRepository: PosterRepository,
    private val releaseYearRepository: ReleaseYearRepository,
    private val ratingRepository: RatingRepository,
    private val votesRepository: VotesRepository
) : ViewModel() {

    private var _movie: MutableLiveData<MovieItemDetail> = MutableLiveData()
    val movie: LiveData<MovieItemDetail> = _movie

    fun getMovie(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val jobMovie: Deferred<Movie> =
                async { movieRepository.getMovie(movieId = movieId) }
            val jobPoster: Deferred<Poster?> =
                async { posterRepository.getPoster(movieId = movieId.toInt()) }
            val jobRating: Deferred<Rating?> =
                async { ratingRepository.getRating(movieId = movieId.toInt()) }
            val jobReleaseYear: Deferred<ReleaseYear?> =
                async { releaseYearRepository.getReleaseYear(movieId = movieId.toInt()) }
            val jobVote: Deferred<Votes?> =
                async { votesRepository.getVotes(movieId = movieId.toInt()) }

            _movie.postValue(
                getMovieItemDetail(
                    jobMovie.await(),
                    jobPoster.await(),
                    jobRating.await(),
                    jobReleaseYear.await(),
                    jobVote.await()
                )
            )
        }
    }

    private fun getMovieItemDetail(
        movie: Movie,
        poster: Poster?,
        rating: Rating?,
        releaseYear: ReleaseYear?,
        votes: Votes?
    ) = MovieItemDetail(
        movie = movie,
        poster = poster,
        rating = rating,
        releaseYear = releaseYear,
        vote = votes
    )
}
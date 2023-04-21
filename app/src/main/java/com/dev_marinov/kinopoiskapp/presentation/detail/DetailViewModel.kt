package com.dev_marinov.kinopoiskapp.presentation.detail

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.DeleteMovieUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import com.dev_marinov.kinopoiskapp.presentation.detail.model.MovieItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val movieRepository: MovieRepository,
    private val posterRepository: PosterRepository,
    private val releaseYearRepository: ReleaseYearRepository,
    private val ratingRepository: RatingRepository,
    private val votesRepository: VotesRepository,
    private val genresRepository: GenresRepository,
    private val personsRepository: PersonsRepository,
    private val videosRepository: VideosRepository
) : ViewModel() {

    private var _movie: MutableLiveData<MovieItemDetail> = MutableLiveData()
    val movie: LiveData<MovieItemDetail> = _movie

    private var _heightPoster: MutableStateFlow<Int> = MutableStateFlow(0)
    val heightPoster: StateFlow<Int> = _heightPoster

    fun getMovie(movieId: String) {

        //Log.d("4444", " DetailViewModel movieId=" + movieId)
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
            val jobGenres: Deferred<List<Genres>> =
                async { genresRepository.getGenres(movieId = movieId.toInt()) }
            val jobPerson: Deferred<List<Person>> =
                async { personsRepository.getPersons(movieId = movieId.toInt()) }
            val jobVideos: Deferred<List<Trailer>?> =
                async { videosRepository.getTrailersForDetail(movieId = movieId.toInt()) }
            _movie.postValue(
                getMovieItemDetail(
                    jobMovie.await(),
                    jobPoster.await(),
                    jobRating.await(),
                    jobReleaseYear.await(),
                    jobVote.await(),
                    jobGenres.await(),
                    jobPerson.await(),
                    jobVideos.await()
                )
            )

            getHeightUrlPoster(_movie.value?.poster?.url)
        }
    }

    private fun getMovieItemDetail(
        movie: Movie,
        poster: Poster?,
        rating: Rating?,
        releaseYear: ReleaseYear?,
        votes: Votes?,
        genres: List<Genres>,
        persons: List<Person>,
        videos: List<Trailer>?
    ) = MovieItemDetail(
        movie = movie,
        poster = poster,
        rating = rating,
        releaseYear = releaseYear,
        vote = votes,
        genres = genres,
        persons = persons,
        videos = videos
    )

    private fun getHeightUrlPoster(url: String?) {
        url?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val stream = URL(url).openStream()
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeStream(stream, null, options)
                _heightPoster.value = options.outHeight
            }
        }
    }
}
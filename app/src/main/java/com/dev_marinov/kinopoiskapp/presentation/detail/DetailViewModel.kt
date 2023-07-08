package com.dev_marinov.kinopoiskapp.presentation.detail

import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.model.movie.*
import com.dev_marinov.kinopoiskapp.presentation.detail.model.MovieItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository,
    private val posterRepository: com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository,
    private val releaseYearRepository: com.dev_marinov.kinopoiskapp.domain.repository.ReleaseYearRepository,
    private val ratingRepository: com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository,
    private val votesRepository: com.dev_marinov.kinopoiskapp.domain.repository.VotesRepository,
    private val genresRepository: com.dev_marinov.kinopoiskapp.domain.repository.GenresRepository,
    private val personsRepository: com.dev_marinov.kinopoiskapp.domain.repository.PersonsRepository,
    private val videosRepository: com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
) : ViewModel() {

    private var _movie: MutableLiveData<MovieItemDetail> = MutableLiveData()
    val movie: LiveData<MovieItemDetail> = _movie

    private var _youtubeUrlBody: MutableStateFlow<String> = MutableStateFlow("")
    val youtubeUrlBody: StateFlow<String> = _youtubeUrlBody

    private var _isClosedTransition: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isClosedTransition: StateFlow<Boolean> = _isClosedTransition

    private var playCount = 0

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
            }
        }
    }

    fun getTrailers(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val trailers = videosRepository.getTrailersForDetail(movieId = movieId)
            trailers?.let {
                getCycle(it)
            }
        }
    }

    private fun getCycle(trailers: List<Trailer>) {
        val youtubeUrlsBodyTemp = mutableListOf<String>()
        var youtubeTrailerBody = ""
        for (i in trailers.indices) {
            if (trailers[i].site == "youtube") {
                val youtubeTrailerUrl: String = trailers[i].url
                val containsEquals = youtubeTrailerUrl.contains("=")
                youtubeTrailerBody = if (containsEquals) {
                    youtubeTrailerUrl.split("=")[1]
                } else {
                    youtubeTrailerUrl.substringAfterLast('/')
                }
                youtubeUrlsBodyTemp.add(youtubeTrailerBody)
            }
        }

        if (youtubeUrlsBodyTemp.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                _youtubeUrlBody.value = youtubeUrlsBodyTemp[playCount]
            }
        }
    }

    fun closeTransition(isClosedTransition: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isClosedTransition.value = isClosedTransition
        }
    }
}
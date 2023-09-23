package com.dev_marinov.kinopoiskapp.presentation.detail

import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.model.movie.*
import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.*
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
    private val getMovieUseCase: GetMovieUseCase,
    private val getPostersUseCase: GetPostersUseCase,
    private val getReleaseYearUseCase: GetReleaseYearUseCase,
    private val getRatingUseCase: GetRatingUseCase,
    private val getVotesUseCase: GetVotesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val getPersonsUseCase: GetPersonsUseCase
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
                async { getMovieUseCase.getMovie(movieId = movieId) }
            val jobPoster: Deferred<Result<Poster?>> =
                async { getPostersUseCase.invoke(GetPostersUseCase.GetPostersParams(movieId = movieId.toInt())) }
            val jobRating: Deferred<Result<Rating?>> =
                async { getRatingUseCase.invoke(GetRatingUseCase.GetRatingParams(movieId = movieId.toInt())) }
            val jobReleaseYear: Deferred<Result<ReleaseYear?>> =
                async { getReleaseYearUseCase.invoke(GetReleaseYearUseCase.GetReleaseYearParams(movieId = movieId.toInt())) }
            val jobVote: Deferred<Result<Votes?>> =
                async { getVotesUseCase.invoke(GetVotesUseCase.GetVotesParams(movieId = movieId.toInt())) }
            val jobGenres: Deferred<Result<List<Genres>>> =
                async { getGenresUseCase.invoke(GetGenresUseCase.GetGenresParams(movieId = movieId.toInt())) }
            val jobPerson: Deferred<Result<List<Person>>> =
                async { getPersonsUseCase.invoke(GetPersonsUseCase.GetPersonsParams(movieId = movieId.toInt())) }
            val jobVideos: Deferred<Result<List<Trailer>?>> =
                async { getVideosUseCase.invoke(GetVideosUseCase.GetVideosParams(movieId = movieId.toInt())) }

            _movie.postValue(
                getMovieItemDetail(
                    jobMovie.await(),
                    jobPoster.await().getOrNull(),
                    jobRating.await().getOrNull(),
                    jobReleaseYear.await().getOrNull(),
                    jobVote.await().getOrNull(),
                    jobGenres.await().getOrNull() ?: listOf(),
                    jobPerson.await().getOrNull() ?: listOf(),
                    jobVideos.await().getOrNull() ?: listOf()
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
            val trailers =
                getVideosUseCase.invoke(GetVideosUseCase.GetVideosParams(movieId = movieId))
            getCycle(trailers.getOrNull() ?: listOf())
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
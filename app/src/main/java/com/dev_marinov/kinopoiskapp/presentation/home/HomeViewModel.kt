package com.dev_marinov.kinopoiskapp.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.data.video.local.VideosDao
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import com.dev_marinov.kinopoiskapp.presentation.home.util.CombineFlows
import com.dev_marinov.kinopoiskapp.presentation.home.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val movieRepository: MovieRepository,
    private val posterRepository: PosterRepository,
    private val releaseYearRepository: ReleaseYearRepository,
    private val ratingRepository: RatingRepository,
    private val votesRepository: VotesRepository,
    private val genresRepository: GenresRepository,
    private val personsRepository: PersonsRepository,
    private val videosRepository: VideosRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val videosDao: VideosDao
) : ViewModel() {
    //
    private val page = 1
    private var previousScrollPosition = 0

    // private val _flagShow: MutableLiveData<Boolean> = MutableLiveData()
    var isHide: Flow<Boolean?> = dataStoreRepository.getHide

    private val flowMovies: Flow<List<Movie>> = getMovies()
    private val flowMoviesIds: Flow<Set<Int>> =
        flowMovies.map { it.map { movie -> movie.id }.toSet() }
    private val flowPosters: Flow<List<Poster>> = getPosters()
    private val flowReleaseYears: Flow<List<ReleaseYear>> = getReleaseYears()
    private val flowRatings: Flow<List<Rating>> = getRatings()
    private val flowVotes: Flow<List<Votes>> = getVotes()
    private val flowGenres: Flow<List<Genres>> = getGenres()
    private val flowPersons: Flow<List<Person>> = getPersons()
    private val flowVideos: Flow<List<Videos>> = getVideos()

    private var _result: MutableStateFlow<List<Videos>> = MutableStateFlow(emptyList())
    //val result: Flow<List<Videos>> = getV()
    ////
////    //    Items which we should show on screen
////    val movieItems: Flow<List<MovieItem>> = getItems()

    init {
       // getData()
        viewModelScope.launch(Dispatchers.IO) {
            topBottomBarHide(false)
        }
    }

//    private fun getV() : Flow<List<Videos>> {
//        return videosRepository.videos
//    }

    suspend fun saveScroll(currentScrollPosition: MutableState<Int>) {
        val isHide: Boolean = previousScrollPosition < currentScrollPosition.value
        previousScrollPosition = currentScrollPosition.value
        dataStoreRepository.saveScroll(Constants.SCROLL_DOWN_KEY, isHide)
    }

    suspend fun topBottomBarHide(isHide: Boolean) {
        dataStoreRepository.saveScroll(Constants.SCROLL_DOWN_KEY, isHide = isHide)
    }

//    suspend fun getScroll() : Flow<Boolean> {
//        return dataStoreRepository.getScroll(Constants.SCROLL_DOWN_KEY)
//    }

//    fun scrollPosition(currentScrollPosition: MutableState<Int>) {
//        _flagShow.value = previousScrollPosition >= currentScrollPosition.value
//        previousScrollPosition = currentScrollPosition.value
//    }

    //    /**
//     * When we click on [Movie], we delete it from database.
//     * This should cause the removal of elements associated with this movie: [Rating], [ReleaseYear], [Poster] and [Votes].
//     * Removal is launched through a [DeleteMovieUseCase]
//     */
    fun onMovieClickedHideBar(isHide: Boolean) {
        viewModelScope.launch {
            topBottomBarHide(isHide = isHide)
        }
    }

//    // потом для удаления пользовать или для лайка
//    fun onMovieClicked(movieItem: MovieItem) {
//        viewModelScope.launch {
//            //deleteMovieUseCase(DeleteMovieUseCase.DeleteMovieParams(movieItem.movie))
//        }
//    }
//

    private fun getData() {
        viewModelScope.launch {
            updateMoviesUseCase.invoke(
                UpdateMoviesUseCase.UpdateMoviesParams("", "")
//              UpdateMoviesUseCase.UpdateMoviesParams(page.toString(), "20")
            )
        }
    }

    //
////    TODO: maybe delete or edit
////    private fun getData() {
////        viewModelScope.launch(Dispatchers.IO) {
////            val response = movieRepository.updateMovies("7-10", "2017-2020", "2", "1", "-1")
////            Log.d("4444", " ListViewModel response=" + response)
////
////
//////            response?.let {
//////                _viewState.value = it
//////            }
////        }
////    }
//
    private fun getMovies(): Flow<List<Movie>> = movieRepository.movies.map { movies ->
        movies.filter { movie ->
            movie.page == page
        }
    }

    private fun getReleaseYears(): Flow<List<ReleaseYear>> {
        return releaseYearRepository.releaseYears.combine(flowMoviesIds) { years, ids ->
            years.filter {
                it.movieId in ids
            }
        }
    }

    private fun getPosters(): Flow<List<Poster>> {
        return posterRepository.posters.combine(flowMoviesIds) { posters, ids ->
            posters.filter {
                it.movieId in ids
            }
        }
    }

    private fun getRatings(): Flow<List<Rating>> {
        return ratingRepository.ratings.combine(flowMoviesIds) { ratings, ids ->
            ratings.filter {
                it.movieId in ids
            }
        }
    }

    private fun getVotes(): Flow<List<Votes>> {
        return votesRepository.votes.combine(flowMoviesIds) { votes, ids ->
            votes.filter {
                it.movieId in ids
            }
        }
    }

    private fun getGenres(): Flow<List<Genres>> {
        return genresRepository.genres.combine(flowMoviesIds) { genres, ids ->
            genres.filter {
                it.movieId in ids
            }
        }
    }

    private fun getPersons(): Flow<List<Person>> {
        return personsRepository.persons.combine(flowMoviesIds) { persons, ids ->
            persons.filter {
                it.movieId in ids
            }
        }
    }

     fun getVideos(): Flow<List<Videos>> {
        return videosRepository.videos.combine(flowMoviesIds) { videos, ids ->
            videos.filter {
                it.movieId in ids
            }
        }
    }

    var movie = CombineFlows.combine(
        flowMovies,
        flowReleaseYears,
        flowPosters,
        flowRatings,
        flowVotes,
        flowPersons,
        flowGenres,
        flowVideos
    ) { listMovies, listYears, listPosters, listRatings, listVotes, listPersons, listGenres, listVideos ->
        listMovies.map { movie ->
            MovieItem(
                movie = movie,
                releaseYears = listYears.filter {
                    it.movieId == movie.id
                },
                poster = listPosters.firstOrNull {
                    it.movieId == movie.id
                },
                rating = listRatings.firstOrNull {
                    it.movieId == movie.id
                },
                votes = listVotes.firstOrNull {
                    it.movieId == movie.id
                },
                persons = listPersons.filter {
                    it.movieId == movie.id
                },
                genres = listGenres.filter {
                    it.movieId == movie.id
                },
                videos = listVideos.firstOrNull {
                    it.movieId == movie.id
                }
            )
        }
    }


//
//
////    /**
////     * When we click on [Movie], we delete it from database.
////     * This should cause the removal of elements associated with this movie: [Rating], [ReleaseYear], [Poster] and [Votes].
////     * Removal is launched through a [DeleteMovieUseCase]
////     */
////    fun onMovieClicked(movieItem: MovieItem) {
////        viewModelScope.launch {
////            deleteMovieUseCase(DeleteMovieUseCase.DeleteMovieParams(movieItem.movie))
////        }
////    }
////
////    private fun getData() {
////        viewModelScope.launch {
////            updateMoviesUseCase.invoke(
////                UpdateMoviesUseCase.UpdateMoviesParams("", "")
//////                UpdateMoviesUseCase.UpdateMoviesParams(page.toString(), "20")
////            )
////        }
////    }
////
//////    TODO: maybe delete or edit
//////    private fun getData() {
//////        viewModelScope.launch(Dispatchers.IO) {
//////            val response = movieRepository.updateMovies("7-10", "2017-2020", "2", "1", "-1")
//////            Log.d("4444", " ListViewModel response=" + response)
//////
//////
////////            response?.let {
////////                _viewState.value = it
////////            }
//////        }
//////    }
//
}

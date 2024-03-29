package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.common.Constants
import com.dev_marinov.kinopoiskapp.domain.model.movie.*
import com.dev_marinov.kinopoiskapp.domain.model.movie_combine.MovieItemCombine
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import com.dev_marinov.kinopoiskapp.domain.usecase.*
import com.dev_marinov.kinopoiskapp.presentation.home.util.CombineFlows
import com.dev_marinov.kinopoiskapp.presentation.util.SortingParamsChipSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val getPostersUseCase: GetPostersUseCase,
    private val getReleaseYearUseCase: GetReleaseYearUseCase,
    private val getRatingUseCase: GetRatingUseCase,
    private val getVotesUseCase: GetVotesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val getLottieAnimationUseCase: GetLottieAnimationUseCase,
    private val getDataStoreUseCase: GetDataStoreUseCase,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val connectivity = connectivityObserver.observe()

    val isPlayingLottie: Flow<Boolean> = getLottieAnimationUseCase.getPlayingLottieFlow()

    val getGradientColorApp: Flow<List<Color>> = getDataStoreUseCase.gradientColorAppFlow
    private val getGradientColorIndexApp: Flow<Int> = getDataStoreUseCase.gradientColorIndexAppFlow

    val responseCodeAllMovies = getMovieUseCase.responseCodeAllMovies

    var page = 1
    private var previousScrollPosition = 0

    private val _isHideTopBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isHideTopBar: StateFlow<Boolean> = _isHideTopBar

    private var flowMovies: Flow<List<Movie>> = getMovies()
    private val flowMoviesIds: Flow<Set<Int>> =
        flowMovies.map { it.map { movie -> movie.id }.toSet() }
    private val flowPosters: Flow<List<Poster>> = getPosters()
    private var flowReleaseYears: Flow<List<ReleaseYear>> = getReleaseYears()
    private var flowRatings: Flow<List<Rating>> = getRatings()
    private val flowVotes: Flow<List<Votes>> = getVotes()
    private val flowGenres: Flow<List<Genres>> = getGenres()
    private val flowPersons: Flow<List<Person>> = getPersons()
    private val flowVideos: Flow<List<Videos>> = getVideos()

    private var _selectChipIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectChipIndex: StateFlow<Int> = _selectChipIndex
    private var _selectGenres: MutableStateFlow<String> = MutableStateFlow("")

    private var _pagingParams = MutableStateFlow<PagingParams?>(null)
    val pagingParams: StateFlow<PagingParams?> = _pagingParams

    private val sortParamsChipSelection: MutableStateFlow<SortingParamsChipSelection> =
        MutableStateFlow(SortingParamsChipSelection.RATING)

    init {
        initViewAction()
        setFirstGradient()
        viewModelScope.launch(Dispatchers.IO) {
            topBottomBarHide(false)
        }
        getParams("movie")
    }

    private fun setFirstGradient() {
        viewModelScope.launch(Dispatchers.IO) {
            getGradientColorIndexApp.collect {
                setGradientColorApp(it)
            }
        }
    }

    private fun setGradientColorApp(selectedBoxIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataStoreUseCase.setGradientColorApp(selectedBoxIndex = selectedBoxIndex)
        }
        saveGradientColorIndexApp(selectedBoxIndex = selectedBoxIndex)
    }

    private fun saveGradientColorIndexApp(selectedBoxIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataStoreUseCase.saveGradientColorIndexApp(
                Constants.CLICKED_GRADIENT_INDEX,
                selectedBoxIndex
            )
        }
    }

    private fun initViewAction() {
        val genre = "movie"
        sortMoviesGenresSelection(genresSelection = genre)
        getShowBottomSheet(typeGenre = genre)
        getCountGenreTypeForBottomNavigationBar(typeGenre = genre)
    }

    fun saveScroll(currentScrollPosition: MutableState<Int>) {
        if (currentScrollPosition.value > previousScrollPosition) {
            _isHideTopBar.value = true
            previousScrollPosition = currentScrollPosition.value
        } else {
            _isHideTopBar.value = false
            previousScrollPosition = currentScrollPosition.value
        }
    }

    suspend fun topBottomBarHide(isHide: Boolean?) {
        isHide?.let {
            getDataStoreUseCase.saveScroll(Constants.SCROLL_DOWN_KEY, isHide = isHide)
        }
    }

    fun onMovieClickedHideNavigationBar(isHide: Boolean) {
        viewModelScope.launch {
            topBottomBarHide(isHide = isHide)
        }
    }

    fun onClickedShowBottomSheet() {
        viewModelScope.launch {
            getDataStoreUseCase.saveClickedFilter(Constants.SHOW_BOTTOM_SHEET_KEY, true)
            delay(1000L)
            getDataStoreUseCase.saveClickedFilter(Constants.SHOW_BOTTOM_SHEET_KEY, false)
        }
    }

    fun getData(pagingParams: PagingParams) {
        viewModelScope.launch {
            val fromToYears = pagingParams.yearPickerFrom.plus("-").plus(pagingParams.yearPickerTo)
            val fromToRatings =
                pagingParams.ratingPickerFrom.plus("-").plus(pagingParams.ratingPickerTo)
            updateMoviesUseCase.invoke(
                UpdateMoviesUseCase.UpdateMoviesParams(
                    fromToYears = fromToYears,
                    fromToRatings = fromToRatings,
                    genre = pagingParams.genre.lowercase(),
                    page = pagingParams.page
                )
            )
        }
    }

    private fun getMovies(): Flow<List<Movie>> = getMovieUseCase.movies.map { movies ->
        movies.filter { movie ->
            movie.page == page
        }
    }

    private fun getReleaseYears(): Flow<List<ReleaseYear>> {
        return getReleaseYearUseCase.getReleaseYearFlow().combine(flowMoviesIds) { years, ids ->
            years.filter {
                it.movieId in ids
            }
        }
    }

    private fun getPosters(): Flow<List<Poster>> {
        return getPostersUseCase.getPostersFlow().combine(flowMoviesIds) { posters, ids ->
            posters.filter {
                it.movieId in ids
            }
        }
    }

    private fun getRatings(): Flow<List<Rating>> {
        return getRatingUseCase.getRatingsFlow().combine(flowMoviesIds) { ratings, ids ->
            ratings.filter {
                it.movieId in ids
            }
        }
    }

    private fun getVotes(): Flow<List<Votes>> {
        return getVotesUseCase.getVotesFlow().combine(flowMoviesIds) { votes, ids ->
            votes.filter {
                it.movieId in ids
            }
        }
    }

    private fun getGenres(): Flow<List<Genres>> {
        return getGenresUseCase.getGenresFlow().combine(flowMoviesIds) { genres, ids ->
            genres.filter {
                it.movieId in ids
            }
        }
    }

    private fun getPersons(): Flow<List<Person>> {
        return getPersonsUseCase.getPersonsFlow().combine(flowMoviesIds) { persons, ids ->
            persons.filter {
                it.movieId in ids
            }
        }
    }

    private fun getVideos(): Flow<List<Videos>> {
        return getVideosUseCase.getVideosFlow().combine(flowMoviesIds) { videos, ids ->
            videos.filter {
                it.movieId in ids
            }
        }
    }

    fun sortMoviesChipSelection(selectChipIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectChipIndex.value = selectChipIndex
            if (selectChipIndex == 0) {
                sortParamsChipSelection.update { SortingParamsChipSelection.RATING }
            }
            if (selectChipIndex == 1) {
                sortParamsChipSelection.update { SortingParamsChipSelection.YEAR }
            }
            if (selectChipIndex == 2) {
                sortParamsChipSelection.update { SortingParamsChipSelection.NAME }
            }
        }
    }

    fun sortMoviesGenresSelection(genresSelection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectGenres.value = genresSelection
            getMovieUseCase.getSortByGenres(genre = genresSelection.lowercase())
            // getParams(selectGenres = genresSelection) // пока не нужно
        }
    }

    private var flagTempClosure = true
    fun getShowBottomSheet(typeGenre: String) {
        flagTempClosure = true
        saveClickedGenreType(typeGenre)
        viewModelScope.launch(Dispatchers.IO) {
            val res: Flow<Int> = getMovieUseCase.getHasGenreDataForBottomSheet(selectGenres = typeGenre)
            res.collect {
                if (it == 0 && flagTempClosure) {
                    onClickedShowBottomSheet()
                    flagTempClosure = false
                }
            }
        }
    }

    fun getCountGenreTypeForBottomNavigationBar(typeGenre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieUseCase.getHasGenreDataForBottomNavigationBar(selectGenres = typeGenre)
        }
    }

    private fun saveClickedGenreType(typeGenre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataStoreUseCase.saveGenreType(Constants.CLICKED_GENRE_TYPE_KEY,
                typeGenre = typeGenre
            )
        }
    }
    private val favoriteMovies: Flow<List<SelectableFavoriteMovie>> = getFavoriteUseCase.getFavoriteMoviesForHomeFlow()
    val movie = CombineFlows.combine(
        flowMovies,
        flowReleaseYears,
        flowPosters,
        flowRatings,
        flowVotes,
        flowPersons,
        flowGenres,
        flowVideos,
        sortParamsChipSelection
    ) { listMovies, listReleaseYears, listPosters, listRatings, listVotes, listPersons, listGenres, listVideos, sortParams ->
        val movies = listMovies.map { movie ->
            MovieItemCombine(
                movie = movie,
                releaseYears = listReleaseYears.filter {
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
        when (sortParams) {
            SortingParamsChipSelection.RATING -> movies.sortedBy { it.rating?.kp }
            SortingParamsChipSelection.YEAR -> movies.sortedBy { it.movie.year }
            SortingParamsChipSelection.NAME -> movies.sortedBy { it.movie.name }
        }
        // не получаю данные для all из за того что нет такого типа all
    }.map {
        it.filter { movieItemCombine ->
            if (_selectGenres.value.lowercase() == "all") {
                movieItemCombine.movie == movieItemCombine.movie // выбрать все фильмы
            } else {
                movieItemCombine.movie.type == _selectGenres.value.lowercase() // выбрать по genre
            }
        }
    }

    val newFavoriteMovies: Flow<List<SelectableFavoriteMovie>> =
        combine(movie, favoriteMovies) { movie, favoriteMovies ->
            movie.map {
                SelectableFavoriteMovie(
                    movie = it,
                    isFavorite = favoriteMovies.contains(
                        SelectableFavoriteMovie(
                            movie = it,
                            isFavorite = it.isFavorite
                        )
                    )
                )
            }
        }

    fun isPlayingLottie(isPlaying: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getLottieAnimationUseCase.executeAnimation(GetLottieAnimationUseCase.GetLottieAnimationParam(isPlaying = isPlaying))
//            movieRepository.playingLottieAnimation(isPlaying = isPlaying)
        }
    }

    fun onClickFavorite(movie: SelectableFavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                getFavoriteUseCase.executeSave(GetFavoriteUseCase.GetFavoriteMovieParams(movie = movie))
            } else {
                getFavoriteUseCase.executeDelete(GetFavoriteUseCase.GetFavoriteMovieParams(movie = movie))
            }
        }
    }

    private fun getParams(genresSelection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val pagingParams = getDataStoreUseCase.getPagingParams(key = genresSelection)
            _pagingParams.value = pagingParams
        }
    }
}
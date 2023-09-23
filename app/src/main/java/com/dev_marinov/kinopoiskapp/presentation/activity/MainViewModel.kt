package com.dev_marinov.kinopoiskapp.presentation.activity

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.common.Constants
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.dev_marinov.kinopoiskapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase,
    private val lottieAnimationUseCase: GetLottieAnimationUseCase,
    private val getDataStoreUseCase: GetDataStoreUseCase,
    private val favoriteUseCase: GetFavoriteUseCase,
) : ViewModel() {

    val getGradientColorApp: Flow<List<Color>> = getDataStoreUseCase.gradientColorAppFlow
    private val getGradientColorIndexApp: Flow<Int> = getDataStoreUseCase.gradientColorIndexAppFlow

    val countSelectGenre: Flow<Int> = getMovieUseCase.countSelectGenre
    val countFavorite: Flow<Int> = favoriteUseCase.getCountFavoriteFlow()

    val isHideBottomBar: Flow<Boolean?> = getDataStoreUseCase.hideBottomBarFlow
    var clickedFilter = getDataStoreUseCase.clickedFilterFlow
    val clickedTypeGenre = getDataStoreUseCase.clickedTypeGenreFlow

    private var _currentRoute: MutableStateFlow<String> = MutableStateFlow("")
    val currentRoute: StateFlow<String> = _currentRoute

    private var _yearPickerFrom: MutableStateFlow<Int> = MutableStateFlow(2000)
    val yearPickerFrom: StateFlow<Int> = _yearPickerFrom
    private var _yearPickerTo: MutableStateFlow<Int> = MutableStateFlow(2000)
    val yearPickerTo: StateFlow<Int> = _yearPickerTo

    private var _ratingPickerFrom: MutableStateFlow<Int> = MutableStateFlow(0)
    val ratingPickerFrom: StateFlow<Int> = _ratingPickerFrom
    private var _ratingPickerTo: MutableStateFlow<Int> = MutableStateFlow(10)
    val ratingPickerTo: StateFlow<Int> = _ratingPickerTo

    init {
        setFirstGradient()
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

    fun selectedYearPickerFrom(yearPickerFrom: Int) {
        _yearPickerFrom.value = yearPickerFrom
    }

    fun selectedYearPickerTo(yearPickerTo: Int) {
        _yearPickerTo.value = yearPickerTo
    }

    fun selectedRatingPickerFrom(ratingPickerFrom: Int) {
        _ratingPickerFrom.value = ratingPickerFrom
    }

    fun selectedRatingPickerTo(ratingPickerTo: Int) {
        _ratingPickerTo.value = ratingPickerTo
    }

    fun bottomSheetParams(
        yearPickerFrom: Int,
        yearPickerTo: Int,
        ratingPickerFrom: Int,
        ratingPickerTo: Int,
        genre: String,
        page: Int
    ) {
        val fromToYears =
            numbersTransformation(pickerFrom = yearPickerFrom, pickerTo = yearPickerTo)
        val fromToRatings =
            numbersTransformation(pickerFrom = ratingPickerFrom, pickerTo = ratingPickerTo)
        getMovies(
            fromToYears = fromToYears,
            fromToRatings = fromToRatings,
            genre = genre.lowercase(),
            page = page
        )
    }

    private fun getMovies(fromToYears: String, fromToRatings: String, genre: String, page: Int) {
        viewModelScope.launch {
            updateMoviesUseCase.invoke(
                UpdateMoviesUseCase.UpdateMoviesParams(
                    fromToYears = fromToYears,
                    fromToRatings = fromToRatings,
                    genre = genre,
                    page = page
                )
            )
        }
    }

    private fun numbersTransformation(pickerFrom: Int, pickerTo: Int): String {
        return if (pickerFrom > pickerTo) {
            pickerTo.toString().plus("-").plus(pickerFrom.toString())
        } else {
            pickerFrom.toString().plus("-").plus(pickerTo.toString())
        }
    }

    fun setPlayingLottie(isPlaying: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            lottieAnimationUseCase.executeAnimation(GetLottieAnimationUseCase.GetLottieAnimationParam(isPlaying = isPlaying))
        }
    }

    fun savePagingParams(
        yearPickerFrom: Int,
        yearPickerTo: Int,
        ratingPickerFrom: Int,
        ratingPickerTo: Int,
        genre: String,
        page: Int,
        indexLoad: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataStoreUseCase.savePagingParams(
                key = genre.lowercase(),
                pagingParams = PagingParams(
                    yearPickerFrom = yearPickerFrom.toString(),
                    yearPickerTo = yearPickerTo.toString(),
                    ratingPickerFrom = ratingPickerFrom.toString(),
                    ratingPickerTo = ratingPickerTo.toString(),
                    genre = genre,
                    page = page,
                    indexLoad = indexLoad
                )
            )
        }
    }

    fun saveCurrentRoute(route: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentRoute.value = route
        }
    }
}
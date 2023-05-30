package com.dev_marinov.kinopoiskapp.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.usecase.UpdateMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val updateMoviesUseCase: UpdateMoviesUseCase
) :
    ViewModel() {

    val countSelectGenre: Flow<Int> = movieRepository.countSelectGenre

    val countMovies: Flow<Int> = getCount()
    val isHideBottomBar: Flow<Boolean?> = dataStoreRepository.getHideBottomBar
    var clickedFilter = dataStoreRepository.getClickedFilter
    val clickedTypeGenre = dataStoreRepository.getClickedTypeGenre

    private var _genresIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val genresIndex: StateFlow<Int> = _genresIndex
    private var _yearPickerFrom: MutableStateFlow<Int> = MutableStateFlow(2000)
    val yearPickerFrom: StateFlow<Int> = _yearPickerFrom
    private var _yearPickerTo: MutableStateFlow<Int> = MutableStateFlow(2000)
    val yearPickerTo: StateFlow<Int> = _yearPickerTo

    private var _ratingPickerFrom: MutableStateFlow<Int> = MutableStateFlow(0)
    val ratingPickerFrom: StateFlow<Int> = _ratingPickerFrom
    private var _ratingPickerTo: MutableStateFlow<Int> = MutableStateFlow(10)
    val ratingPickerTo: StateFlow<Int> = _ratingPickerTo

    private fun getCount(): Flow<Int> { return movieRepository.countMovies }




    fun selectedGenresIndex(selectedGenresIndex: Int) { _genresIndex.value = selectedGenresIndex }
    fun selectedYearPickerFrom(yearPickerFrom: Int) { _yearPickerFrom.value = yearPickerFrom }
    fun selectedYearPickerTo(yearPickerTo: Int) { _yearPickerTo.value = yearPickerTo }
    fun selectedRatingPickerFrom(ratingPickerFrom: Int) { _ratingPickerFrom.value = ratingPickerFrom }
    fun selectedRatingPickerTo(ratingPickerTo: Int) { _ratingPickerTo.value = ratingPickerTo }


    // сюда надо передать genre от топ бар (убираю из ботом бар список genres)
    fun bottomSheetParams(
        yearPickerFrom: Int,
        yearPickerTo: Int,
        ratingPickerFrom: Int,
        ratingPickerTo: Int,
        genre: String
    ) {
        val fromToYears = numbersTransformation(pickerFrom = yearPickerFrom, pickerTo = yearPickerTo)
        val fromToRatings = numbersTransformation(pickerFrom = ratingPickerFrom, pickerTo = ratingPickerTo)
        getData(fromToYears = fromToYears, fromToRatings = fromToRatings, genre = genre.lowercase())
    }

    private fun getData(fromToYears: String, fromToRatings: String, genre: String) {
        viewModelScope.launch {
            updateMoviesUseCase.invoke(
                UpdateMoviesUseCase.UpdateMoviesParams(fromToYears = fromToYears, fromToRatings = fromToRatings, genre = genre)
//              UpdateMoviesUseCase.UpdateMoviesParams(page.toString(), "20")
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
}
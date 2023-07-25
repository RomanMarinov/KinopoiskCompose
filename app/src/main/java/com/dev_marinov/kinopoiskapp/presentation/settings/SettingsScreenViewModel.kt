package com.dev_marinov.kinopoiskapp.presentation.settings

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.common.Constants
import com.dev_marinov.kinopoiskapp.domain.usecase.GetDataStoreUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.GetFavoriteUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val getDataStoreUseCase: GetDataStoreUseCase,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val connectivity = connectivityObserver.observe()
    val gradientColorsApp: Flow<List<List<Color>>> = getDataStoreUseCase.gradientColorsAppFlow
   val getGradientColorApp: Flow<List<Color>> = getDataStoreUseCase.gradientColorAppFlow
    val getGradientColorIndexApp: Flow<Int> = getDataStoreUseCase.gradientColorIndexAppFlow

    val getCountStatAll: Flow<Int> = getMovieUseCase.countStatAll
    val getCountStatMovies: Flow<Int> = getMovieUseCase.countStatMovies
    val getCountStatTvSeries: Flow<Int> = getMovieUseCase.countStatTvSeries
    val getCountStatCartoon: Flow<Int> = getMovieUseCase.countStatCartoon
    val getCountStatAnime: Flow<Int> = getMovieUseCase.countStatAnime
    val getCountStatAnimatedSeries: Flow<Int> = getMovieUseCase.countStatAnimatedSeries
    val countFavorite: Flow<Int> = getFavoriteUseCase.getCountFavoriteFlow()

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

    fun setGradientColorApp(selectedBoxIndex: Int) {
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

    fun onClearMoviesClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieUseCase.getClearAllMovies()
            getFavoriteUseCase.executeClear()
        }
    }
}
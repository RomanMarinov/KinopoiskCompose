package com.dev_marinov.kinopoiskapp.presentation.settings

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStoreRepository: com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository,
    private val movieRepository: com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository,
    private val favoriteRepository: com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val connectivity = connectivityObserver.observe()

    val gradientColorsApp: Flow<List<List<Color>>> = dataStoreRepository.gradientColorsApp
    val getGradientColorApp: Flow<List<Color>> = dataStoreRepository.getGradientColorApp
    val getGradientColorIndexApp: Flow<Int> = dataStoreRepository.getGradientColorIndexApp

    val getCountStatAll: Flow<Int> = movieRepository.countStatAll
    val getCountStatMovies: Flow<Int> = movieRepository.countStatMovies
    val getCountStatTvSeries: Flow<Int> = movieRepository.countStatTvSeries
    val getCountStatCartoon: Flow<Int> = movieRepository.countStatCartoon
    val getCountStatAnime: Flow<Int> = movieRepository.countStatAnime
    val getCountStatAnimatedSeries: Flow<Int> = movieRepository.countStatAnimatedSeries
    val countFavorite: Flow<Int> = favoriteRepository.countFavorite

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
            dataStoreRepository.setGradientColorApp(selectedBoxIndex = selectedBoxIndex)
        }
        saveGradientColorIndexApp(selectedBoxIndex = selectedBoxIndex)
    }

    private fun saveGradientColorIndexApp(selectedBoxIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveGradientColorIndexApp(
                Constants.CLICKED_GRADIENT_INDEX,
                selectedBoxIndex
            )
        }
    }

    // потом для удаления пользовать или для лайка
    fun onClearMoviesClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.clearAllMovies()
            favoriteRepository.clearAllMovies()
        }
    }
}
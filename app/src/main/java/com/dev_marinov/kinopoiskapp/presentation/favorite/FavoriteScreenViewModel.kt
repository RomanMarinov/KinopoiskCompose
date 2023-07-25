package com.dev_marinov.kinopoiskapp.presentation.favorite

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.common.Constants
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import com.dev_marinov.kinopoiskapp.domain.usecase.GetDataStoreUseCase
import com.dev_marinov.kinopoiskapp.domain.usecase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val getDataStoreUseCase: GetDataStoreUseCase,
    connectivityObserver: ConnectivityObserver
) :
    ViewModel() {

    val connectivity = connectivityObserver.observe()

    val getGradientColorApp: Flow<List<Color>> = getDataStoreUseCase.gradientColorAppFlow
    val favoriteMovies: LiveData<List<SelectableFavoriteMovie>> = getFavoriteUseCase.getFavoriteMoviesForDetailFlow()

    fun onClickFavorite(movie: SelectableFavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                getFavoriteUseCase.executeSave(GetFavoriteUseCase.GetFavoriteMovieParams(movie = movie))
            } else {
                getFavoriteUseCase.executeDelete(GetFavoriteUseCase.GetFavoriteMovieParams(movie = movie))
            }
        }
    }

    fun onMovieClickedHideNavigationBar(isHide: Boolean) {
        viewModelScope.launch {
            topBottomBarHide(isHide = isHide)
        }
    }

    suspend fun topBottomBarHide(isHide: Boolean?) {
        isHide?.let {
                getDataStoreUseCase.saveScroll(Constants.SCROLL_DOWN_KEY, isHide = isHide)
        }
    }
}
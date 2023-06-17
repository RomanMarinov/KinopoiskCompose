package com.dev_marinov.kinopoiskapp.presentation.favorite


import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import com.dev_marinov.kinopoiskapp.domain.repository.FavoriteRepository
import com.dev_marinov.kinopoiskapp.presentation.home.util.Constants
import com.dev_marinov.kinopoiskapp.presentation.model.SelectableFavoriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val dataStoreRepository: DataStoreRepository
)
: ViewModel() {

    val getGradientColorApp: Flow<List<Color>> = dataStoreRepository.getGradientColorApp
    val favoriteMovies: LiveData<List<SelectableFavoriteMovie>> = favoriteRepository.favoriteMoviesForDetail

    val favoriteMoviesForHome = favoriteRepository.favoriteMoviesForHome

    fun onClickFavorite(movie: SelectableFavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("4444", " movie=" + movie)
            if (movie.isFavorite) {
                favoriteRepository.saveFavoriteMovie(movie = movie)
            } else {
                favoriteRepository.deleteFavoriteMovie(movie = movie)
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
            dataStoreRepository.saveScroll(Constants.SCROLL_DOWN_KEY, isHide = isHide)
        }
    }
}
package com.dev_marinov.kinopoisk.presentation.show_list.componets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoisk.domain.repository.KinopoiskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val kinopoiskRepository: KinopoiskRepository) :
    ViewModel() {

//    private val _viewState: MutableStateFlow<KinopoiskResponse> = MutableStateFlow()
//    val viewState = _viewState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = kinopoiskRepository.getMovies("7-10", "2017-2020", "2", "1", "-1")
            Log.d("4444", " ListViewModel response=" + response)



//            response?.let {
//                _viewState.value = it
//            }
        }
    }
}
package com.dev_marinov.kinopoisk.presentation.show_list.componets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.kinopoisk.domain.model.Doc
import com.dev_marinov.kinopoisk.domain.repository.KinopoiskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val kinopoiskRepository: KinopoiskRepository) :
    ViewModel() {

    private val _viewState: MutableStateFlow<List<Doc>> = MutableStateFlow(emptyList())
    val viewState = _viewState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = kinopoiskRepository.getData("7-10", "207-2020", "2", "1", "-1")
            Log.d("4444", " ListViewModel response=" + response)
            response?.let {
                _viewState.value = it
            }
        }
    }
}
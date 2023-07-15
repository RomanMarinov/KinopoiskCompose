package com.dev_marinov.kinopoiskapp.domain.usecase

import androidx.compose.ui.graphics.Color
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val hideBottomBarFlow: Flow<Boolean?> = dataStoreRepository.getHideBottomBar

    suspend fun saveScroll(key: String, isHide: Boolean) {
        withContext(dispatcher) {
            dataStoreRepository.saveScroll(key, isHide)
        }
    }

    val clickedFilterFlow: Flow<Boolean> = dataStoreRepository.getClickedFilter

    suspend fun saveClickedFilter(key: String, isShow: Boolean) {
        withContext(dispatcher) {
            dataStoreRepository.saveClickedFilter(key, isShow)
        }
    }

    val clickedTypeGenreFlow: Flow<String> = dataStoreRepository.getClickedTypeGenre

    suspend fun saveGenreType(key: String, typeGenre: String) {
        withContext(dispatcher) {
            dataStoreRepository.saveGenreType(key, typeGenre)
        }
    }

    val gradientColorsAppFlow: Flow<List<List<Color>>> = dataStoreRepository.gradientColorsApp

    val gradientColorAppFlow: Flow<List<Color>> = dataStoreRepository.getGradientColorApp

    suspend fun setGradientColorApp(selectedBoxIndex: Int) {
        withContext(dispatcher) {
            dataStoreRepository.setGradientColorApp(selectedBoxIndex)
        }
    }

    val gradientColorIndexAppFlow: Flow<Int> = dataStoreRepository.getGradientColorIndexApp

    suspend fun saveGradientColorIndexApp(key: String, selectedBoxIndex: Int) {
        withContext(dispatcher) {
            dataStoreRepository.saveGradientColorIndexApp(key, selectedBoxIndex)
        }
    }

    suspend fun savePagingParams(key: String, pagingParams: PagingParams) {
        withContext(dispatcher) {
            dataStoreRepository.savePagingParams(key, pagingParams)
        }
    }

    suspend fun getPagingParams(key: String): PagingParams? {
        return withContext(dispatcher) {
            dataStoreRepository.getPagingParams(key)
        }
    }
}
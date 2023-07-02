package com.dev_marinov.kinopoiskapp.domain.repository

import android.graphics.Color
import androidx.compose.ui.graphics.Color
import com.dev_marinov.kinopoiskapp.presentation.model.PagingParams
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val getHideBottomBar: Flow<Boolean?>
    suspend fun saveScroll(key: String, isHide: Boolean)

    val getClickedFilter: Flow<Boolean>
    suspend fun saveClickedFilter(key: String, isShow: Boolean)

    val getClickedTypeGenre: Flow<String>
    suspend fun saveGenreType(key: String, typeGenre: String)

    val gradientColorsApp: Flow<List<List<android.graphics.Color>>>

    val getGradientColorApp: Flow<List<android.graphics.Color>>
    suspend fun setGradientColorApp(selectedBoxIndex: Int)

    val getGradientColorIndexApp: Flow<Int>
    suspend fun saveGradientColorIndexApp(key: String, selectedBoxIndex: Int)

//    val getPagingParams : Flow<PagingParams>
    suspend fun savePagingParams(key: String, pagingParams: PagingParams)

    suspend fun getPagingParams(key: String): PagingParams?
}
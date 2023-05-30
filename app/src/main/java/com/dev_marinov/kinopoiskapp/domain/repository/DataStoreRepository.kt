package com.dev_marinov.kinopoiskapp.domain.repository


import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val getHideBottomBar: Flow<Boolean?>
    suspend fun saveScroll(key: String, isHide: Boolean)

    val getClickedFilter: Flow<Boolean>
    suspend fun saveClickedFilter(key: String, isShow: Boolean)

    val getClickedTypeGenre: Flow<String>
    suspend fun saveGenreType(key: String, typeGenre: String)
}
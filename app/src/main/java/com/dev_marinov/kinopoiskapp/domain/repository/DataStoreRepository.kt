package com.dev_marinov.kinopoiskapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val getHide: Flow<Boolean?>
    suspend fun saveScroll(key: String, isHide: Boolean)
    //suspend fun getScroll(key: String) : Flow<Boolean?>
}
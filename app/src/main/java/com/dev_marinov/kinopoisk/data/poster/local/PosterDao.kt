package com.dev_marinov.kinopoisk.data.poster.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PosterDao {

    @Query("SELECT * FROM posters")
    suspend fun getAllFlow(): Flow<List<PosterEntity>>
}
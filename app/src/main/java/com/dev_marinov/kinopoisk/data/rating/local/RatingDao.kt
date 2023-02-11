package com.dev_marinov.kinopoisk.data.rating.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Query("SELECT * FROM ratings")
    suspend fun getAllFlow(): Flow<List<RatingEntity>>
}
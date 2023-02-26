package com.dev_marinov.kinopoiskapp.data.rating.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Query("SELECT * FROM ratings")
    fun getAllFlow(): Flow<List<RatingEntity>>
}
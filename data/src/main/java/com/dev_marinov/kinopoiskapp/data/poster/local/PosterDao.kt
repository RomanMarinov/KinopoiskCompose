package com.dev_marinov.kinopoiskapp.data.poster.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PosterDao {

    @Query("SELECT * FROM posters")
    fun getAllFlow(): Flow<List<PosterEntity>>

    @Query("SELECT * FROM posters WHERE movie_id = :movie_id")
    fun getPosterForDetail(movie_id: Int): PosterEntity?
}
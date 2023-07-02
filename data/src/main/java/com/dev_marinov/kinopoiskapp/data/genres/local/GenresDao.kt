package com.dev_marinov.kinopoiskapp.data.genres.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres")
    fun getAllFlow(): Flow<List<GenresEntity>>

    @Query("SELECT * FROM genres WHERE movie_id = :movie_id")
    fun getGenresForDetail(movie_id: Int): List<GenresEntity>
}
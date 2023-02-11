package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page")
    suspend fun getAllByPage(page: String): List<MovieEntity>

    @Query("SELECT * FROM movies")
    suspend fun getAllFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)
}
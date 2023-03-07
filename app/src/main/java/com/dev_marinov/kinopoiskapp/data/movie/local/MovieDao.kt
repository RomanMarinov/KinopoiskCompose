package com.dev_marinov.kinopoiskapp.data.movie.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page")
    suspend fun getAllByPage(page: String): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieForDetail(id: String?): MovieEntity

    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)
}
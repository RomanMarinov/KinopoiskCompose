package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.*
import com.dev_marinov.kinopoisk.domain.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page")
    suspend fun getAllByPage(page: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)
}
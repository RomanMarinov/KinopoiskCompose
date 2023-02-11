package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.*
import com.dev_marinov.kinopoisk.domain.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page")
    fun getAllByPage(page: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)
}
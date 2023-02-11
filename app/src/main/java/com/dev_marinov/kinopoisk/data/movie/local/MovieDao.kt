package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.*
import com.dev_marinov.kinopoisk.data.movie.remote.GetMoviesResponse
import com.dev_marinov.kinopoisk.domain.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM kinopoisk WHERE page = :page")
    fun getData(page: String): GetMoviesResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)
}
package com.dev_marinov.kinopoiskapp.data.releaseYear.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReleaseYearDao {

    @Query("SELECT * FROM release_years")
    fun getAllFlow(): Flow<List<ReleaseYearEntity>>

    // my fun
    @Query("SELECT * FROM release_years WHERE movie_id = :movie_id")
    fun getReleaseYearForDetail(movie_id: Int): ReleaseYearEntity
}
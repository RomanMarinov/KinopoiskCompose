package com.dev_marinov.kinopoisk.data.poster.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dev_marinov.kinopoisk.data.relations.MovieAndPosterId
import kotlinx.coroutines.flow.Flow

@Dao
interface PosterDao {

    @Query("SELECT * FROM posters")
    fun getAllFlow(): Flow<List<PosterEntity>>

    @Transaction
    @Query(value = "SELECT * FROM posters WHERE poster_id = :poster_id")
    suspend fun getPosterFromMovie(poster_id: String) : Flow<MovieAndPosterId>
}
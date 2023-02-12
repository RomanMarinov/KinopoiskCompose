package com.dev_marinov.kinopoisk.data.rating.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dev_marinov.kinopoisk.data.poster.local.PosterEntity
import com.dev_marinov.kinopoisk.data.relations.MovieAndRatingId
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Query("SELECT * FROM ratings")
    fun getAllFlow(): Flow<List<RatingEntity>>

    @Transaction
    @Query(value = "SELECT * FROM ratings WHERE rating_id = :rating_id")
    suspend fun getRatingFromMovie(rating_id: String) : Flow<MovieAndRatingId>
}
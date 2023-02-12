package com.dev_marinov.kinopoisk.data.votes.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dev_marinov.kinopoisk.data.rating.local.RatingEntity
import com.dev_marinov.kinopoisk.data.relations.MovieAndVotesId
import kotlinx.coroutines.flow.Flow

@Dao
interface VotesDao {

    @Query("SELECT * FROM votes")
    fun getAllFlow(): Flow<List<VotesEntity>>

    @Transaction
    @Query(value = "SELECT * FROM votes WHERE votes_id = :votes_id")
    suspend fun getVotesFromMovie(votes_id: String) : Flow<MovieAndVotesId>
}
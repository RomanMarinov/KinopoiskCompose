package com.dev_marinov.kinopoiskapp.data.votes.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VotesDao {

    @Query("SELECT * FROM votes")
    fun getAllFlow(): Flow<List<VotesEntity>>

    @Query("SELECT * FROM votes WHERE movie_id = :movie_id")
    fun getVotesForDetail(movie_id: Int): VotesEntity?
}
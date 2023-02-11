package com.dev_marinov.kinopoisk.data.votes.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VotesDao {

    @Query("SELECT * FROM votes")
    suspend fun getAllFlow(): Flow<List<VotesEntity>>
}
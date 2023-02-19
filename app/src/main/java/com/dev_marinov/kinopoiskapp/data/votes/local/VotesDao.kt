package com.dev_marinov.kinopoiskapp.data.votes.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VotesDao {

    @Query("SELECT * FROM votes")
    fun getAllFlow(): Flow<List<VotesEntity>>
}
package com.dev_marinov.kinopoisk.data.releaseYear.local

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface ReleaseYearDao {

    @Query("SELECT * FROM release_years")
    suspend fun getAllFlow(): Flow<List<ReleaseYearEntity>>
}
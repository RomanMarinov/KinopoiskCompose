package com.dev_marinov.kinopoiskapp.data.persons.local

import androidx.room.Dao
import androidx.room.Query
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonsDao {
    @Query("SELECT * FROM persons")
    fun getAllFlow(): Flow<List<PersonsEntity>>

    @Query("SELECT * FROM persons WHERE movie_id = :movie_id")
    fun getPersonsForDetail(movie_id: Int): List<PersonsEntity>
}
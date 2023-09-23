package com.dev_marinov.kinopoiskapp.data.video.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideosDao {

    @Query("SELECT * FROM videos")
    fun getAllFlow(): Flow<List<VideosEntity>>

    @Query("SELECT * FROM videos WHERE movie_id = :movie_id")
    fun getTrailers(movie_id: Int): VideosEntity
}
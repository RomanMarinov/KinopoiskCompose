package com.dev_marinov.kinopoiskapp.data.video.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import com.dev_marinov.kinopoiskapp.data.DataConvertersForList
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.Videos
import kotlinx.coroutines.flow.Flow

@Dao
interface VideosDao {

    @Query("SELECT * FROM videos")
    fun getAllFlow(): Flow<List<VideosEntity>>

    @Query("SELECT * FROM videos WHERE movie_id = :movie_id")
    fun getTrailers(movie_id: Int) : VideosEntity
}
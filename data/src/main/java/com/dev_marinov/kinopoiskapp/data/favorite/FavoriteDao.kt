package com.dev_marinov.kinopoiskapp.data.favorite

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavoriteEntity)

    @Delete
    suspend fun delete(movie: FavoriteEntity)

    @Query("SELECT * FROM favorite_movie WHERE isFavorite = 1 ORDER BY timestamp ASC")
    fun getFavorites(): LiveData<List<FavoriteEntity>>

    @Query("SELECT COUNT(id) FROM favorite_movie")
    fun getCountFavorite(): Flow<Int>

    @Query("DELETE FROM favorite_movie")
    fun clearAllMovies()
}

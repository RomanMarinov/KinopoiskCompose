package com.dev_marinov.kinopoiskapp.data.movie.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieForDetail(id: String?): MovieEntity

    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT COUNT(id) FROM movies")
    fun getRowCount(): Flow<Int>

    @Query("SELECT * FROM movies ORDER BY year ASC")
    fun sortingASCYear(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies ORDER BY name ASC")
    fun sortingASCName(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE type = :genre")
    fun sortingGenre(genre: String): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun hasGenreData(selectGenres: String): Flow<Int>

    @Query("DELETE FROM movies")
    fun clearAllMovies()

    @Query("SELECT COUNT(id) FROM movies")
    fun getCountStatAll(): Flow<Int>
    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun getCountStatMovie(selectGenres: String): Flow<Int>
    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun getCountStatTvSeries(selectGenres: String): Flow<Int>
    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun getCountStatCartoon(selectGenres: String): Flow<Int>
    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun getCountStatAnime(selectGenres: String): Flow<Int>
    @Query("SELECT COUNT(*) FROM movies WHERE type = :selectGenres")
    fun getCountStatAnimatedSeries(selectGenres: String): Flow<Int>
}
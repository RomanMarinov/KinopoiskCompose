package com.dev_marinov.kinopoisk.data.common.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.dev_marinov.kinopoisk.domain.model.Votes

interface CommonDao {

    @Transaction
    suspend fun insertData(
        movie: Movie,
        releaseYears: List<ReleaseYear>,
        votes: Votes?,
        rating: Rating?,
        poster: Poster?
    ) {
        insertMovie(movie)
        insertReleaseYears(releaseYears)
        insertVotes(votes)
        insertRating(rating)
        insertPoster(poster)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReleaseYears(releaseYears: List<ReleaseYear>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVotes(votes: Votes?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: Rating?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoster(poster: Poster?)
}
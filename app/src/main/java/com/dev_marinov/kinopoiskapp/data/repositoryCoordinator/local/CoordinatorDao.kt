package com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingEntity
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesEntity

@Dao
interface CoordinatorDao {

    // если я делал несколько операций  и одна не прошла, то все остальные откататься
    @Transaction
    suspend fun insertData(
        movie: MovieEntity,
        releaseYears: List<ReleaseYearEntity>,
        votes: VotesEntity?,
        rating: RatingEntity?,
        poster: PosterEntity?
    ) {
        insertMovie(movie)
        insertReleaseYears(releaseYears)
        insertVotes(votes)
        insertRating(rating)
        insertPoster(poster)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReleaseYears(releaseYears: List<ReleaseYearEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVotes(votes: VotesEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: RatingEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoster(poster: PosterEntity?)
}
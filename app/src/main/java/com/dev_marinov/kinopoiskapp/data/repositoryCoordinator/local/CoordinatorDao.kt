package com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.dev_marinov.kinopoiskapp.data.genres.local.GenresEntity
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsEntity
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingEntity
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.data.video.local.VideosEntity
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
        poster: PosterEntity?,
        genres: List<GenresEntity>?,
        persons: List<PersonsEntity>?,
        videos: VideosEntity?
    ) {
        insertMovie(movie)
        insertReleaseYears(releaseYears)
        insertVotes(votes)
        insertRating(rating)
        insertPoster(poster)
        insertGenres(genres)
        insertPersons(persons)
        insertVideos(videos)
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenresEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonsEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: VideosEntity?)

//    ///////////////
//    @Transaction
//    suspend fun insert(movieEntityWithVideoEntityTags: MovieEntityWithVideoEntityTags) {
//        insert(movieEntityWithVideoEntityTags.movieEntity)
//
//        movieEntityWithVideoEntityTags.trailers?.let {
//            for (i in it) {
//                insert(movieEntityWithVideoEntityTags)
//            }
//        }
//    }
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(movieEntity: MovieEntity?): Long
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(videosEntity: VideosEntity): Long


}
package com.dev_marinov.kinopoiskapp.data.repositoryCoordinator

import com.dev_marinov.kinopoiskapp.data.genres.local.GenresEntity
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsEntity
import com.dev_marinov.kinopoiskapp.data.poster.local.PosterEntity
import com.dev_marinov.kinopoiskapp.data.rating.local.RatingEntity
import com.dev_marinov.kinopoiskapp.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoiskapp.data.repositoryCoordinator.local.CoordinatorDao
import com.dev_marinov.kinopoiskapp.data.video.local.VideosEntity
import com.dev_marinov.kinopoiskapp.data.votes.local.VotesEntity
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.repository.RepositoryCoordinator
import javax.inject.Inject

class RepositoryCoordinatorImpl @Inject constructor(
    private val coordinatorDao: CoordinatorDao,
) : RepositoryCoordinator {

    override suspend fun saveData(
        movie: Movie,
        releaseYears: List<ReleaseYear>,
        votes: Votes?,
        rating: Rating?,
        poster: Poster?,
        genres: List<Genre>?,
        persons: List<Person>?,
        videos: Videos?,
    ) {
        coordinatorDao.insertData(
            // перед записью в бд я должен смапить из домена в энтити
            movie = MovieEntity.mapFromDomain(movie),
            releaseYears = releaseYears.map {
                ReleaseYearEntity.mapFromDomain(it)
            },
            votes = votes?.let {
                VotesEntity.mapFromDomain(it)
            },
            rating = rating?.let {
                RatingEntity.mapFromDomain(it)
            },
            poster = poster?.let {
                PosterEntity.mapFromDomain(it)
            },
            genres = genres?.map {
                GenresEntity.mapFromDomain(it)
            },
            persons = persons?.map {
                PersonsEntity.mapFromDomain(it)
            },
            videos = videos?.let {
                VideosEntity.mapFromDomain(it)
            }
        )
    }
}
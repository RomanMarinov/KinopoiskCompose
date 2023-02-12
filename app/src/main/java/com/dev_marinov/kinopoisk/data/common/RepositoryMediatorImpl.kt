package com.dev_marinov.kinopoisk.data.common

import com.dev_marinov.kinopoisk.data.common.local.CommonDao
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.poster.local.PosterEntity
import com.dev_marinov.kinopoisk.data.rating.local.RatingEntity
import com.dev_marinov.kinopoisk.data.releaseYear.local.ReleaseYearEntity
import com.dev_marinov.kinopoisk.data.votes.local.VotesEntity
import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.dev_marinov.kinopoisk.domain.model.Votes
import com.dev_marinov.kinopoisk.domain.repository.RepositoryMediator
import javax.inject.Inject

class RepositoryMediatorImpl @Inject constructor(
    private val commonDao: CommonDao
) : RepositoryMediator {

    override suspend fun saveData(
        movie: Movie,
        releaseYears: List<ReleaseYear>,
        votes: Votes?,
        rating: Rating?,
        poster: Poster?
    ) {
        commonDao.insertData(
            // перед записью в бд я должен смапить из домена в энтити
            movie = MovieEntity.mapFromDomain(movie),
            releaseYears = releaseYears.map { ReleaseYearEntity.mapFromDomain(it) },
            votes = votes?.let { VotesEntity.mapFromDomain(it) },
            rating = rating?.let { RatingEntity.mapFromDomain(it) },
            poster = poster?.let { PosterEntity.mapFromDomain(it) }
        )
    }
}
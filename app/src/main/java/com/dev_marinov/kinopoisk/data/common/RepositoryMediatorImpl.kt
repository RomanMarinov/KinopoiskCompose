package com.dev_marinov.kinopoisk.data.common

import com.dev_marinov.kinopoisk.data.common.local.CommonDao
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
        commonDao.insertData(movie, releaseYears, votes, rating, poster)
    }
}
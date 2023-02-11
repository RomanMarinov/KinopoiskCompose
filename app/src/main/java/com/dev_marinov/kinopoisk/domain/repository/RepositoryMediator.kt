package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.dev_marinov.kinopoisk.domain.model.Votes

interface RepositoryMediator {

    suspend fun saveData(
        movie: Movie,
        releaseYears: List<ReleaseYear>,
        votes: Votes?,
        rating: Rating?,
        poster: Poster?
    )
}
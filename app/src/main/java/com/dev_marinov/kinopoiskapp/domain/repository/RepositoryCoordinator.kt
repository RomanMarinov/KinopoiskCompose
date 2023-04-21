package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.*

/**
 *
 */
interface RepositoryCoordinator {

    suspend fun saveData(
        movie: Movie,
        releaseYears: List<ReleaseYear>,
        votes: Votes?,
        rating: Rating?,
        poster: Poster?,
        genres: List<Genre>?,
        persons: List<Person>?,
        videos: Videos?
    )
}
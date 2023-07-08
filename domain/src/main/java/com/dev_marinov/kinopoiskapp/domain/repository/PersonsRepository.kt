package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.movie.Person
import kotlinx.coroutines.flow.Flow

interface PersonsRepository {

    val persons: Flow<List<Person>>
    suspend fun getPersons(movieId: Int): List<Person>
}
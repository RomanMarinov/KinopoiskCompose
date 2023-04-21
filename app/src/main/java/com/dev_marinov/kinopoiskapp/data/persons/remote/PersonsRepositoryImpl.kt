package com.dev_marinov.kinopoiskapp.data.persons.remote

import com.dev_marinov.kinopoiskapp.data.persons.local.PersonsDao
import com.dev_marinov.kinopoiskapp.domain.model.Person
import com.dev_marinov.kinopoiskapp.domain.repository.PersonsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonsRepositoryImpl @Inject constructor(private val personsDao: PersonsDao) :
    PersonsRepository {
    override val persons: Flow<List<Person>> = personsDao.getAllFlow().map {
        it.map { personsEntity -> personsEntity.mapToDomain() }
    }

    override suspend fun getPersons(movieId: Int): List<Person> {
        return personsDao.getPersonsForDetail(movie_id = movieId).map {
            it.mapToDomain()
        }
    }
}
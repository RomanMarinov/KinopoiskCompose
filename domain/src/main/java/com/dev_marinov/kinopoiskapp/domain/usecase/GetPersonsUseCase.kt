package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Person
import com.dev_marinov.kinopoiskapp.domain.repository.PersonsRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val personsRepository: PersonsRepository
) : UseCase<GetPersonsUseCase.GetPersonsParams, List<Person>>(Dispatchers.IO) {

    data class GetPersonsParams(val movieId: Int)

    override suspend fun execute(parameters: GetPersonsParams): List<Person> {
        return personsRepository.getPersons(parameters.movieId)
    }

    fun getPersonsFlow(): Flow<List<Person>> {
        return personsRepository.persons
    }
}

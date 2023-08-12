package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Person
import com.dev_marinov.kinopoiskapp.domain.repository.PersonsRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetPersonsUseCaseTest {

    @Mock
    private lateinit var mockPersonsRepository: PersonsRepository
    private lateinit var getPersonsUseCase: GetPersonsUseCase

    @Before
    fun setup() {
        getPersonsUseCase = GetPersonsUseCase(personsRepository = mockPersonsRepository)
    }

    @After
    fun after() {
        Mockito.reset(mockPersonsRepository)
    }

    @Test
    fun `execute return list persons flow from repository`() = runTest {
        val expectedPersonsList = listOf(
            Person(
                id = 0,
                photo = "photo",
                name = "name",
                movieId = 0
            )
        )
        // Mockito.`when`(mockPersonsRepository.persons).thenReturn(flowOf(expectedPersons))
        // С помощью `Mockito` указывается, что при вызове свойства `persons` у макета `mockPersonsRepository`,
        // должен быть возвращен поток (`Flow`) с ожидаемым списком объектов `Person`.
        // Метод `flowOf()` позволяет создать поток из переданного списка объектов.
        `when`(mockPersonsRepository.persons).thenReturn(flowOf(expectedPersonsList))
        val actualResult = getPersonsUseCase.getPersonsFlow().first()

        assertEquals(expectedPersonsList, actualResult)
    }

    @Test
    fun `execute return list person from repository`() = runTest {
        val movieId = 123
        val expectedPersonsList = listOf(
            Person(
                id = 0,
                photo = "photo",
                name = "name",
                movieId = 0
            )
        )

        `when`(mockPersonsRepository.getPersons(movieId = movieId)).thenReturn(expectedPersonsList)
        val actualResult = getPersonsUseCase(GetPersonsUseCase.GetPersonsParams(movieId = movieId))

//        `when`(getPersonsUseCase.invoke(GetPersonsUseCase.GetPersonsParams(movieId = movieId)))
//        val actualResult = mockPersonsRepository.getPersons(movieId = movieId)

        assert(actualResult.isSuccess)
        assert(actualResult.getOrNull() == expectedPersonsList)
    }
}
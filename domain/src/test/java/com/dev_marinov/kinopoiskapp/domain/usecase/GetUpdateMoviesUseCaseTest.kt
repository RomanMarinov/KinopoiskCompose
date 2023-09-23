package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetUpdateMoviesUseCaseTest {

    @Mock
    private lateinit var mockMovieRepository: MovieRepository
    private lateinit var getUpdateMoviesUseCase: UpdateMoviesUseCase

    @Before
    fun setup() {
        getUpdateMoviesUseCase = UpdateMoviesUseCase(movieRepository = mockMovieRepository)
    }

    @After
    fun after() {
        Mockito.reset(mockMovieRepository)
    }

    @Test
    fun `executing movies update from repository`() = runTest {

        // Arrange
        val fromToYears = "2000-2020"
        val fromToRatings = "1-10"
        val genre = "genre"
        val page = 1

        val params = UpdateMoviesUseCase.UpdateMoviesParams(fromToYears, fromToRatings, genre, page)

        getUpdateMoviesUseCase.invoke(params)

        // Assert
        // Mockito.verify для проверки, что метод updateMovies был вызван с правильными аргументами.
        Mockito.verify(mockMovieRepository).updateMovies(fromToYears, fromToRatings, genre, page)
    }
}
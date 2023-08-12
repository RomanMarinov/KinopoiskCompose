package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Genres
import com.dev_marinov.kinopoiskapp.domain.repository.GenresRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetGenresUseCaseTest {

    @Mock
    private lateinit var mockGenresRepository: GenresRepository
    private lateinit var getGenresUseCase: GetGenresUseCase

    @Before
    fun setup() {
        getGenresUseCase = GetGenresUseCase(genresRepository = mockGenresRepository)
    }

//    val genres: Flow<List<Genres>>
//    suspend fun getGenres(movieId: Int): List<Genres>

    @Test
    fun `execute return list genre flow from repository`() = runTest {
        val expectedGenresList = listOf(
            Genres(
                id = 0,
                genres = "genres",
                movieId = 0
            )
        )

        // настройка поведения что при вызове в репозитории свойства genres должен
        // возвращен поток с определенным ожидаемым типом
        `when`(mockGenresRepository.genres).thenReturn(flowOf(expectedGenresList))
        val actualResult = getGenresUseCase.getGenresFlow().first()

        assertEquals(expectedGenresList, actualResult)
    }

    @Test
    fun `execute return genre list from repository`() = runTest {
        val movieId = 123
        val expectedGenresList = listOf(
            Genres(
                id = 0,
                genres = "genres",
                movieId = 0
            )
        )

        `when`(mockGenresRepository.getGenres(movieId = movieId)).thenReturn(expectedGenresList)
        val actualResult = getGenresUseCase(GetGenresUseCase.GetGenresParams(movieId = movieId))

        assert(actualResult.isSuccess)
        assert(actualResult.getOrNull() == expectedGenresList)
    }
}


package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Poster
import com.dev_marinov.kinopoiskapp.domain.repository.PosterRepository
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
class GetPosterUseCaseTest {

    @Mock
    private lateinit var mockPosterRepository: PosterRepository
    private lateinit var getPostersUseCase: GetPostersUseCase

    @Before
    fun setup() {
        getPostersUseCase = GetPostersUseCase(posterRepository = mockPosterRepository)
    }

    @After
    fun after() {
        Mockito.reset(mockPosterRepository)
    }

    @Test
    fun `execute return poster list flow from repository`() = runTest {
        val expectedPosterList = listOf(
            Poster(
                id = 0,
                previewUrl = "preview_url",
                url = "url",
                movieId = 0
            )
        )

        `when`(mockPosterRepository.posters).thenReturn(flowOf(expectedPosterList))
        val actualResult = getPostersUseCase.getPostersFlow().first()
        assertEquals(expectedPosterList, actualResult)
    }

    @Test
    fun `execute return getPoster from repository`() = runTest {
        val movieId = 123
        val expectedPoster = Poster(
            id = 0,
            previewUrl = "preview_url",
            url = "url",
            movieId = 0
        )

        `when`(mockPosterRepository.getPoster(movieId = movieId)).thenReturn(expectedPoster)
        val actualResult = getPostersUseCase(GetPostersUseCase.GetPostersParams(movieId = movieId))
        assertEquals(expectedPoster, actualResult.getOrNull())
    }
}
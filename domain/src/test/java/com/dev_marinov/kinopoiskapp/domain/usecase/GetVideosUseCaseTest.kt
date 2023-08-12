package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.movie.Videos
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetVideosUseCaseTest {

    @Mock
    private lateinit var mockVideosRepository: VideosRepository
    private lateinit var getVideosUseCase: GetVideosUseCase

    @Before
    fun setup() {
        getVideosUseCase = GetVideosUseCase(videosRepository = mockVideosRepository)
    }

    @Test
    fun `execute should return list of trailers from repository`() = runTest {
        val movieId = 123
        val expectedTrailers = listOf(
            Trailer(
                url = "url",
                name = "name",
                site = "site",
                movieId = 0
            )
        )

        `when`(mockVideosRepository.getTrailersForDetail(movieId)).thenReturn(expectedTrailers)

        val actualResult = getVideosUseCase(GetVideosUseCase.GetVideosParams(movieId))

        assert(actualResult.isSuccess)
        assert(actualResult.getOrNull() == expectedTrailers)
    }

    @Test
    fun `getVideosFlow should return flow from repository`() = runTest {
        val expectedVideos = listOf(
            Videos(
                trailers = listOf(
                    Trailer(
                        url = "url",
                        name = "name",
                        site = "site",
                        movieId = 0
                    )
                ),
                movieId = 0
            )
        )
        `when`(mockVideosRepository.videos).thenReturn(flowOf(expectedVideos))
        val actualResult = getVideosUseCase.getVideosFlow().first()
        assertEquals(expectedVideos, actualResult)
    }
}
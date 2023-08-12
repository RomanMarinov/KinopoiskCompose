package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Rating
import com.dev_marinov.kinopoiskapp.domain.repository.RatingRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class GetRatingUseCaseTest {

    @Mock
    private lateinit var mockRatingRepository: RatingRepository
    private lateinit var getRatingUseCase: GetRatingUseCase

    @Before
    fun setup() {
        getRatingUseCase = GetRatingUseCase(ratingRepository = mockRatingRepository)
    }

    @Test
    fun `execute return rating list flow from repository`() = runTest {

        val expectedRatingList = listOf(
            Rating(
                id = 0,
                kp = 0.0,
                imdb = 0.0,
                filmCritics = 0.0,
                russianFilmCritics = 0.0,
                await = 0.0,
                movieId = 0
            )
        )

        `when`(mockRatingRepository.ratings).thenReturn(flowOf(expectedRatingList))
        val actualResult = getRatingUseCase.getRatingsFlow().first()
        assertEquals(expectedRatingList, actualResult)
    }

    @Test
    fun `execute return getRating from repository`() = runTest {
        val movieId = 123
        val expectedRating = Rating(
            id = 0,
            kp = 0.0,
            imdb = 0.0,
            filmCritics = 0.0,
            russianFilmCritics = 0.0,
            await = 0.0,
            movieId = 0
        )

        `when`(mockRatingRepository.getRating(movieId = movieId)).thenReturn(expectedRating)
        val actualResult = getRatingUseCase(GetRatingUseCase.GetRatingParams(movieId = movieId))

        assert(actualResult.isSuccess)
        assert(actualResult.getOrNull() == expectedRating)
    }

}
package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.repository.LottieAnimationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetLottieAnimationUseCaseTest {

    @Mock
    private lateinit var mockLottieAnimationRepository: LottieAnimationRepository
    private lateinit var getLottieAnimationUseCase: GetLottieAnimationUseCase

    @Before
    fun setup() {
        getLottieAnimationUseCase = GetLottieAnimationUseCase(mockLottieAnimationRepository)
    }

    @Test
    fun `executing lottie animation from repository`() = runTest {
        // Arrange
        val isPlaying = true
        val parameters = GetLottieAnimationUseCase.GetLottieAnimationParam(isPlaying)

        // Act
        getLottieAnimationUseCase.executeAnimation(parameters)

        // Assert
        Mockito.verify(mockLottieAnimationRepository).playingLottieAnimation(isPlaying)
    }

    @Test
    fun `test getting playing lottie flow from repository`() = runTest {
        val expectedFlow = flowOf(true)
        `when`(mockLottieAnimationRepository.isPlayingLottie).thenReturn(expectedFlow)

        val expected = true

        val resultFlow = getLottieAnimationUseCase.getPlayingLottieFlow()
        resultFlow.collect { resultActual ->
            assertEquals(expected, resultActual)
        }
    }
}
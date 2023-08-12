package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Votes
import com.dev_marinov.kinopoiskapp.domain.repository.VotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetVotesUseCaseTest {
    @Mock
    private lateinit var mockVotesRepository: VotesRepository
    private lateinit var getVotesUseCase: GetVotesUseCase

    @Before
    fun setup() {
        getVotesUseCase = GetVotesUseCase(repositoryVotes = mockVotesRepository)
    }

    @After
    fun after() {
        Mockito.reset(mockVotesRepository)
    }

    @Test
    fun `execute should return votes from repository`() = runTest {
        val movieId = 123
        val expectedVotes = Votes(
            id = 0,
            kp = 0,
            imdb = 0,
            filmCritics = 0,
            russianFilmCritics = 0,
            await = 0,
            movieId = 0
        )
        `when`(mockVotesRepository.getVotes(movieId)).thenReturn(expectedVotes)
        launch {
            val actualResult = getVotesUseCase(GetVotesUseCase.GetVotesParams(movieId))
            assertEquals(Result.success(expectedVotes), actualResult)
        }
    }

    @Test
    fun `getVotesFlow should return flow of votes from repository`() = runTest {
        val expectedVotesList = listOf(
            Votes(
                id = 0,
                kp = 0,
                imdb = 0,
                filmCritics = 0,
                russianFilmCritics = 0,
                await = 0,
                movieId = 0
            )
        )
        `when`(mockVotesRepository.votes).thenReturn(flowOf(expectedVotesList))
        val actualResult = getVotesUseCase.getVotesFlow().first()
        assertEquals(expectedVotesList, actualResult)
    }
}
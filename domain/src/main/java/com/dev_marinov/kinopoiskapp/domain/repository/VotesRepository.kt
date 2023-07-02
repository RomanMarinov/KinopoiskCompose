package com.dev_marinov.kinopoiskapp.domain.repository

import com.dev_marinov.kinopoiskapp.domain.model.Votes
import kotlinx.coroutines.flow.Flow

interface VotesRepository {
    val votes: Flow<List<Votes>>
    // val vote: Flow<Votes>

    suspend fun getVotes(movieId: Int): Votes?
}
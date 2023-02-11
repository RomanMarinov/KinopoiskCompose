package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Votes
import kotlinx.coroutines.flow.Flow

interface VotesRepository {

    val votes: Flow<List<Votes>>
}
package com.dev_marinov.kinopoisk.data.votes

import com.dev_marinov.kinopoisk.data.votes.local.VotesDao
import com.dev_marinov.kinopoisk.domain.model.Votes
import com.dev_marinov.kinopoisk.domain.repository.VotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VotesRepositoryImpl @Inject constructor(localDataSource: VotesDao) : VotesRepository {

    override val votes: Flow<List<Votes>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }
}
package com.dev_marinov.kinopoiskapp.data.votes

import com.dev_marinov.kinopoiskapp.data.votes.local.VotesDao
import com.dev_marinov.kinopoiskapp.domain.model.Votes
import com.dev_marinov.kinopoiskapp.domain.repository.VotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VotesRepositoryImpl @Inject constructor(private val localDataSource: VotesDao) :
    VotesRepository {

    override val votes: Flow<List<Votes>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    override suspend fun getVotes(movieId: Int): Votes? {
        return localDataSource.getVotesForDetail(movie_id = movieId)?.mapToDomain()
    }
}
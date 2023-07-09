package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.model.movie.Votes
import com.dev_marinov.kinopoiskapp.domain.repository.VotesRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVotesUseCase @Inject constructor(private val repositoryVotes: VotesRepository) :
    UseCase<GetVotesUseCase.GetVotesParams, Votes>(Dispatchers.IO) {

    data class GetVotesParams(val movieId: Int)

    override suspend fun execute(parameters: GetVotesParams): Votes {
        return repositoryVotes.getVotes(movieId = parameters.movieId)
    }

    fun getVotesFlow(): Flow<List<Votes>> {
        return repositoryVotes.votes
    }
}
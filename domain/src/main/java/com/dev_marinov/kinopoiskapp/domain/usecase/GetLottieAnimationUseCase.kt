package com.dev_marinov.kinopoiskapp.domain.usecase

import com.dev_marinov.kinopoiskapp.domain.repository.LottieAnimationRepository
import com.dev_marinov.kinopoiskapp.domain.util.UseCaseLottieAnimation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetLottieAnimationUseCase @Inject constructor(private val lottieAnimationRepository: LottieAnimationRepository) :
    UseCaseLottieAnimation<GetLottieAnimationUseCase.GetLottieAnimationParam>(Dispatchers.IO) {

    data class GetLottieAnimationParam(val isPlaying: Boolean)

    override suspend fun lottieAnimation(parameters: GetLottieAnimationParam) {
        lottieAnimationRepository.playingLottieAnimation(isPlaying = parameters.isPlaying)
    }

    fun getPlayingLottieFlow() : Flow<Boolean> {
        return lottieAnimationRepository.isPlayingLottie
    }

}
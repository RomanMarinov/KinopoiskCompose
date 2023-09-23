package com.dev_marinov.kinopoiskapp.data.lottie_animation

import com.dev_marinov.kinopoiskapp.domain.repository.LottieAnimationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LottieAnimationRepositoryImpl @Inject constructor() : LottieAnimationRepository {

    override val isPlayingLottie: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override suspend fun playingLottieAnimation(isPlaying: Boolean) {
        isPlayingLottie.value = isPlaying
    }
}
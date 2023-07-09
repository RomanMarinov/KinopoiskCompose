package com.dev_marinov.kinopoiskapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface LottieAnimationRepository {
    val isPlayingLottie: Flow<Boolean>
    suspend fun playingLottieAnimation(isPlaying: Boolean)
}
package com.dev_marinov.kinopoiskapp.domain.util

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCaseLottieAnimation <in P>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    // р параметр
    // r возращаемый рез (в моем слу список)
    private val TAG = "UseCase"

    suspend fun executeAnimation(parameters: P) {
        kotlin.runCatching {
            try {
                withContext(coroutineDispatcher) {
                    lottieAnimation(parameters)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                throw exception
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun lottieAnimation(parameters: P)
}
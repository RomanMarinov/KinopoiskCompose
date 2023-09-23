package com.dev_marinov.kinopoiskapp.domain.util

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    // р параметр
    // r возращаемый рез (в моем слу список)
    private val TAG = "UseCase"

    suspend operator fun invoke(parameters: P): Result<R> {
        return kotlin.runCatching {
            try {
                withContext(coroutineDispatcher) {
                    execute(parameters)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                throw exception
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
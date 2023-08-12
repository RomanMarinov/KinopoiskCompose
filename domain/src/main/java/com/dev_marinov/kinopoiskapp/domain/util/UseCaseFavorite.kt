package com.dev_marinov.kinopoiskapp.domain.util

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCaseFavorite<in P>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    // р параметр
    // r возращаемый рез (в моем слу список)
    private val TAG = "UseCase"

     suspend fun executeSave(parameters: P) {
        kotlin.runCatching {
            try {
                withContext(coroutineDispatcher) {
                    save(parameters)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                throw exception
            }
        }
    }

    suspend fun executeDelete(parameters: P) {
        kotlin.runCatching {
            try {
                withContext(coroutineDispatcher) {
                    delete(parameters)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                throw exception
            }
        }
    }

    suspend fun executeClear() {
        kotlin.runCatching {
            try {
                withContext(coroutineDispatcher) {
                    clear()
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                throw exception
            }
        }
    }
    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun save(parameters: P)
    @Throws(RuntimeException::class)
    protected abstract suspend fun delete(parameters: P)
    @Throws(RuntimeException::class)
    protected abstract suspend fun clear()
}
package com.dev_marinov.kinopoiskapp.data.movie.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import com.dev_marinov.kinopoiskapp.presentation.home.util.Constants
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val PREFERENCES_NAME = "FLAG"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class DataStoreRepositoryImpl @Inject constructor(private val context: Context) : DataStoreRepository {

     override val getHide = context.dataStore.data.map { preferences ->
         val preferencesKey = booleanPreferencesKey(Constants.SCROLL_DOWN_KEY)
         preferences[preferencesKey]
    }

    override suspend fun saveScroll(key: String, isHide: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = isHide
        }
    }

//     private  fun getScroll(): Flow<Boolean> {
//        val preferencesKey = booleanPreferencesKey(Constants.SCROLL_DOWN_KEY)
//        val preferences = context.dataStore.data.map {
//                it[preferencesKey]
//
//        }
//
//        return preferences
//    }

//    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { preferences ->
//        val sortOrder = SortOrder.valueOf(preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.NONE.name)
//        val showCompleted = preferences[PreferencesKeys.SHOW_COMPLETED] ?: false
//
//        UserPreferences(showCompleted, sortOrder)
//    }


}
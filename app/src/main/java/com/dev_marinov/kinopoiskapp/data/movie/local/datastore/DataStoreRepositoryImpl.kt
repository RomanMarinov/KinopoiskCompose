package com.dev_marinov.kinopoiskapp.data.movie.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import com.dev_marinov.kinopoiskapp.presentation.home.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private const val PREFERENCES_NAME = "FLAG"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class DataStoreRepositoryImpl @Inject constructor(private val context: Context) : DataStoreRepository {

     override val getHideBottomBar = context.dataStore.data.map { preferences ->
         val preferencesKey = booleanPreferencesKey(Constants.SCROLL_DOWN_KEY)
         preferences[preferencesKey]
    }

    override val getClickedFilter: Flow<Boolean> = context.dataStore.data.map { preferences ->
        val preferencesKey = booleanPreferencesKey(Constants.SHOW_BOTTOM_SHEET_KEY)
        preferences[preferencesKey] == true
    }

    override val getClickedTypeGenre: Flow<String> = context.dataStore.data.map { preferences ->
        val preferencesKey = stringPreferencesKey(Constants.CLICKED_GENRE_TYPE_KEY)
        preferences[preferencesKey] ?: "" // если значение отсутствует, вернуть пустую строку
    }

    override suspend fun saveScroll(key: String, isHide: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = isHide
        }
    }

    override suspend fun saveClickedFilter(key: String, isShow: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = isShow
        }
    }

    override suspend fun saveGenreType(key: String, typeGenre: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = typeGenre
        }
    }
}
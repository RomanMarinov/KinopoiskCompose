package com.dev_marinov.kinopoiskapp.data.movie.local.datastore

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dev_marinov.kinopoiskapp.data.R
import com.dev_marinov.kinopoiskapp.domain.repository.DataStoreRepository
import com.dev_marinov.kinopoiskapp.common.Constants
import com.dev_marinov.kinopoiskapp.data.pagination.PagingParamsDTO
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.google.gson.Gson
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "FLAG"
private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    private val gradientColors1 = listOf(
        ContextCompat.getColor(context, R.color.color1),
        ContextCompat.getColor(context, R.color.color2)
    )
    private val gradientColors2 = listOf(
        ContextCompat.getColor(context, R.color.color3),
        ContextCompat.getColor(context, R.color.color4)
    )
    private val gradientColors3 = listOf(
        ContextCompat.getColor(context, R.color.color5),
        ContextCompat.getColor(context, R.color.color6)
    )
    override val gradientColorsApp: Flow<List<List<Color>>> = flowOf(
        listOf(
            gradientColors1.map { color -> Color(color) }, // преобразование списка Int в список Color
            gradientColors2.map { color -> Color(color) },
            gradientColors3.map { color -> Color(color) }
        )
    )

    override val getGradientColorApp: MutableStateFlow<List<Color>> = MutableStateFlow(emptyList())

    override suspend fun setGradientColorApp(selectedBoxIndex: Int) {
        gradientColorsApp.collect { colorLists ->
            val colors = colorLists[selectedBoxIndex].map {
                val colorInt = it.toArgb()
                Color(colorInt)
            }
            getGradientColorApp.value = colors
        }
    }

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

    override val getGradientColorIndexApp: Flow<Int> = context.dataStore.data.map { preferences ->
        val preferencesKey = intPreferencesKey(Constants.CLICKED_GRADIENT_INDEX)
        preferences[preferencesKey] ?: 0 // если значение отсутствует, вернуть пустую строку
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

    override suspend fun saveGradientColorIndexApp(key: String, selectedBoxIndex: Int) {
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = selectedBoxIndex
        }
    }

    override suspend fun savePagingParams(key: String, pagingParams: PagingParams) {
        val pagingParamsDTO = pagingMapFromDomain(pagingParams = pagingParams)
        val jsonString = Gson().toJson(pagingParamsDTO)
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = jsonString
        }
    }

    override suspend fun getPagingParams(key: String): PagingParams? {
        val preferenceKey = stringPreferencesKey(key)
        val jsonString = context.dataStore.data.firstOrNull()?.get(key = preferenceKey)
        return if (jsonString != null) Gson().fromJson(
            jsonString,
            PagingParams::class.java
        ) else null
    }

    private fun pagingMapFromDomain(pagingParams: PagingParams) : PagingParamsDTO {
        return PagingParamsDTO(
            yearPickerFrom = pagingParams.yearPickerFrom,
            yearPickerTo = pagingParams.yearPickerTo,
            ratingPickerFrom = pagingParams.ratingPickerFrom,
            ratingPickerTo = pagingParams.ratingPickerTo,
            genre = pagingParams.genre,
            page = pagingParams.page,
            indexLoad = pagingParams.indexLoad
        )
    }
}
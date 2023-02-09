package com.dev_marinov.kinopoisk.domain.repository

import com.dev_marinov.kinopoisk.domain.model.Doc
import com.dev_marinov.kinopoisk.domain.model.KinopoiskResponse

interface KinopoiskRepository {

    suspend fun getData(search_rating: String, search_date: String, search_typeNumber: String, sortType_date: String, sortType_rating: String ) : KinopoiskResponse?

}
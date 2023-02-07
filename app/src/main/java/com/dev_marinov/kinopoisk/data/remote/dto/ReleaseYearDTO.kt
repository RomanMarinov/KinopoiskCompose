package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.google.gson.annotations.SerializedName

data class ReleaseYearDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("end")
    val end: Int,
    @SerializedName("start")
    val start: Int
) {
    fun mapToDomain() : ReleaseYear {
        return ReleaseYear(
            _id = _id,
            end = end,
            start = start
        )
    }
}
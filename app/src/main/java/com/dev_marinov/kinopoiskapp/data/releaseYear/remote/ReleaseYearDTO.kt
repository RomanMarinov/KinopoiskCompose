package com.dev_marinov.kinopoiskapp.data.releaseYear.remote

import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear
import com.google.gson.annotations.SerializedName

data class ReleaseYearDTO(
    @SerializedName("_id")
    val id: String,
    @SerializedName("end")
    val end: Int?,
    @SerializedName("start")
    val start: Int
) {
    fun mapToDomain(movieId: Int): ReleaseYear {
        return ReleaseYear(
            id = id,
            end = end,
            start = start,
            movieId = movieId
        )
    }
}
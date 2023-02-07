package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.ExternalId
import com.google.gson.annotations.SerializedName

data class ExternalIdDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("imdb")
    val imdb: String,
    @SerializedName("kpHD")
    val kpHD: String,
    @SerializedName("tmdb")
    val tmdb: Int
) {
    fun mapToDomain() : ExternalId {
        return ExternalId(
            _id = _id,
            imdb = imdb,
            kpHD = kpHD,
            tmdb = tmdb
        )
    }
}
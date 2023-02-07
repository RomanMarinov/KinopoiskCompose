package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Rating
import com.google.gson.annotations.SerializedName

data class RatingDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("await")
    val await: Double,
    @SerializedName("filmCritics")
    val filmCritics: Int,
    @SerializedName("imdb")
    val imdb: Double,
    @SerializedName("kp")
    val kp: Double,
    @SerializedName("russianFilmCritics")
    val russianFilmCritics: Double
) {
    fun mapToDomain() : Rating{
        return Rating(
            _id = _id,
            await = await,
            filmCritics = filmCritics,
            imdb = imdb,
            kp = kp,
            russianFilmCritics = russianFilmCritics
        )
    }
}
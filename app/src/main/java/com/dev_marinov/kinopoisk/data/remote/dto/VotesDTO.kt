package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Votes
import com.google.gson.annotations.SerializedName

data class VotesDTO(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("await")
    val await: Int,
    @SerializedName("filmCritics")
    val filmCritics: Int,
    @SerializedName("imdb")
    val imdb: Int,
    @SerializedName("kp")
    val kp: Int,
    @SerializedName("russianFilmCritics")
    val russianFilmCritics: Int
) {
    fun mapToDomain() : Votes {
        return Votes(
            _id = _id,
            await = await,
            filmCritics = filmCritics,
            imdb = imdb,
            kp = kp,
            russianFilmCritics = russianFilmCritics
        )
    }
}
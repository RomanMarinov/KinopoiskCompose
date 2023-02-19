package com.dev_marinov.kinopoiskapp.data.votes.remote

import com.dev_marinov.kinopoiskapp.domain.model.Votes
import com.google.gson.annotations.SerializedName

data class VotesDTO(
    @SerializedName("_id")
    val id: String,
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
    fun mapToDomain(movieId: Int): Votes {
        return Votes(
            id = id,
            await = await,
            filmCritics = filmCritics,
            imdb = imdb,
            kp = kp,
            russianFilmCritics = russianFilmCritics,
            movieId = movieId
        )
    }
}
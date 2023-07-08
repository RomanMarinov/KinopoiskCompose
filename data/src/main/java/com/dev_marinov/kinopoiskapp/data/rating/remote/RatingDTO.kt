package com.dev_marinov.kinopoiskapp.data.rating.remote

import com.dev_marinov.kinopoiskapp.domain.model.movie.Rating
import com.google.gson.annotations.SerializedName

data class RatingDTO(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("await")
    val await: Double,
    @SerializedName("filmCritics")
    val filmCritics: Double,
    @SerializedName("imdb")
    val imdb: Double,
    @SerializedName("kp")
    val kp: Double,
    @SerializedName("russianFilmCritics")
    val russianFilmCritics: Double
) {
    fun mapToDomain(movieId: Int): Rating {
        return Rating(
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
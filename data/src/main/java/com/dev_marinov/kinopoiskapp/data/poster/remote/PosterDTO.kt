package com.dev_marinov.kinopoiskapp.data.poster.remote

import com.dev_marinov.kinopoiskapp.domain.model.Poster
import com.google.gson.annotations.SerializedName

data class PosterDTO(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("previewUrl")
    val previewUrl: String,
    @SerializedName("url")
    val url: String
) {
    fun mapToDomain(movieId: Int): Poster {
        return Poster(
            id = id,
            previewUrl = previewUrl,
            url = url,
            movieId = movieId
        )
    }
}
package com.dev_marinov.kinopoisk.data.poster.remote

import com.dev_marinov.kinopoisk.domain.model.Poster
import com.google.gson.annotations.SerializedName

data class PosterDTO(
    @SerializedName("_id")
    val id: String,
    @SerializedName("previewUrl")
    val previewUrl: String,
    @SerializedName("url")
    val url: String
) {
    fun mapToDomain(movieId:Int) : Poster {
        return Poster(
            id = id,
            previewUrl = previewUrl,
            url = url,
            movieId = movieId
        )
    }
}
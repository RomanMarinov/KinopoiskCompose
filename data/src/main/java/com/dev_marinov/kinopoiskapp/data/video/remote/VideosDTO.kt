package com.dev_marinov.kinopoiskapp.data.video.remote

import com.dev_marinov.kinopoiskapp.domain.model.Videos
import com.google.gson.annotations.SerializedName

data class VideosDTO(
    @SerializedName("trailers")
    val trailers: List<TrailerDTO>
) {
    fun mapToDomain(movieId: Int): Videos {
        return Videos(
            trailers = trailers.map { it.mapToDomain(movieId = movieId) },
            movieId = movieId
        )
    }
}

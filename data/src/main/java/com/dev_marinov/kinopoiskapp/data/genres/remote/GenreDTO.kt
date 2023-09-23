package com.dev_marinov.kinopoiskapp.data.genres.remote

import com.dev_marinov.kinopoiskapp.domain.model.movie.Genre
import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    fun mapToDomain(movieId: Int): Genre {
        return Genre(
            id = id,
            name = name,
            movieId = movieId
        )
    }
}

package com.dev_marinov.kinopoisk.data.remote.dto

import com.google.gson.annotations.SerializedName

data class KinopoiskResponseDTO(
    @SerializedName("docs")
    val movies: List<MovieDTO>,
    @SerializedName("page")
    val page: Int
)
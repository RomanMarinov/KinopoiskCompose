package com.dev_marinov.kinopoiskapp.data.movie.remote

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("docs")
    val movies: List<MovieDTO>,
    @SerializedName("page")
    val page: Int
)
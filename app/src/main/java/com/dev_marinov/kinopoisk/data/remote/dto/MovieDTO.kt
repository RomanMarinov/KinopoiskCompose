package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.*
import com.google.gson.annotations.SerializedName


data class MovieDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("alternativeName")
    val alternativeName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("length")
    val length: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("releaseYears")
    val releaseYears: List<ReleaseYearDTO>?,
    @SerializedName("votes")
    val votes: VotesDTO?,
    @SerializedName("rating")
    val rating: RatingDTO?,
    @SerializedName("poster")
    val poster: PosterDTO?
) {
    fun mapToDomain(page: Int): Movie {
        return Movie(
            alternativeName = alternativeName,
            description = description,
            id = id,
            name = name,
            poster = poster?.mapToDomain(),
            rating = rating?.mapToDomain(),
            releaseYears = releaseYears?.map {
                it.mapToDomain()
            },
            shortDescription = shortDescription,
            type = type,
            votes = votes?.mapToDomain(),
            year = year,
            page = page,
            length = length
        )
    }
}



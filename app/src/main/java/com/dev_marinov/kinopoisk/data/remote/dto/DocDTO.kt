package com.dev_marinov.kinopoisk.data.remote.dto

import com.dev_marinov.kinopoisk.domain.model.Doc
import com.google.gson.annotations.SerializedName


data class DocDTO(
    @SerializedName("externalId")
    val externalId: ExternalIdDTO,
    @SerializedName("logo")
    val logo: LogoDTO,
    @SerializedName("poster")
    val poster: PosterDTO,
    @SerializedName("rating")
    val rating: RatingDTO,
    @SerializedName("votes")
    val votes: VotesDTO,
    @SerializedName("watchability")
    val watchability: WatchabilityDTO?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("alternativeName")
    val alternativeName: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("enName")
    val enName: Any,
    @SerializedName("names")
    val names: List<NameDTO>,
    @SerializedName("movieLength")
    val movieLength: Int,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("releaseYears")
    val releaseYears: List<ReleaseYearDTO>,
) {
    fun mapToDomain(): Doc {
        return Doc(
            alternativeName = alternativeName,
            color = color,
            description = description,
            enName = enName,
            externalId = externalId.mapToDomain(),
            id = id,
            logo = logo.mapToDomain(),
            movieLength = movieLength,
            name = name,
            names = names.map {
                it.mapToDomain()
            },
            poster = poster.mapToDomain(),
            rating = rating.mapToDomain(),
            releaseYears = releaseYears.map {
                it.mapToDomain()
            },
            shortDescription = shortDescription,
            type = type,
            votes = votes.mapToDomain(),
            watchability = watchability?.mapToDomain(),
            year = year
        )
    }
}

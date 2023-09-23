package com.dev_marinov.kinopoiskapp.data.movie.remote

import com.dev_marinov.kinopoiskapp.data.genres.remote.GenreDTO
import com.dev_marinov.kinopoiskapp.data.persons.remote.PersonsDTO
import com.dev_marinov.kinopoiskapp.data.poster.remote.PosterDTO
import com.dev_marinov.kinopoiskapp.data.rating.remote.RatingDTO
import com.dev_marinov.kinopoiskapp.data.releaseYear.remote.ReleaseYearDTO
import com.dev_marinov.kinopoiskapp.data.video.remote.VideosDTO
import com.dev_marinov.kinopoiskapp.data.votes.remote.VotesDTO
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.dev_marinov.kinopoiskapp.domain.model.movie.Movie
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("movieLength")
    val movieLength: Int?,
    @SerializedName("year")
    val year: Int?,

    @SerializedName("releaseYears")
    val releaseYears: List<ReleaseYearDTO>?,
    @SerializedName("votes")
    val votes: VotesDTO?,
    @SerializedName("rating")
    val rating: RatingDTO?,
    @SerializedName("poster")
    val poster: PosterDTO?,
    @SerializedName("genres")
    val genres: List<GenreDTO>?, // только GenreDTO
    @SerializedName("persons")
    val persons: List<PersonsDTO>?,
//    @SerializedName("videos")
//    val videos: List<TrailerDTO>?,
    @SerializedName("videos")
    val videos: VideosDTO,
) {
    fun mapToDomain(page: Int): Movie {
        return Movie(
            id = id,
            type = type,
            name = name,
            description = description,
            movieLength = movieLength,
            year = year,
            page = page
        )
    }
}
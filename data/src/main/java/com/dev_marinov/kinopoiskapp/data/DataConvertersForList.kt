package com.dev_marinov.kinopoiskapp.data

import androidx.room.TypeConverter
import com.dev_marinov.kinopoiskapp.domain.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConvertersForList {

    @TypeConverter
    fun fromVideos(videos: Videos): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<Videos?>() {}.type
        return gson.toJson(videos, type)
    }

    @TypeConverter
    fun toVideos(videos: String?): Videos? {
        if (videos == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Videos>() {}.type
        return gson.fromJson<Videos>(videos, type)
    }

    @TypeConverter
    fun fromStringToMovie(value: String?): Movie? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Movie>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromMovieToString(value: Movie?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromReleaseYearListToString(trailer: List<ReleaseYear?>?): String? {
        if (trailer == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ReleaseYear?>?>() {}.type
        return gson.toJson(trailer, type)
    }

    @TypeConverter
    fun fromStringToReleaseYearList(trailer: String?): List<ReleaseYear>? {
        if (trailer == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ReleaseYear?>?>() {}.type
        return gson.fromJson<List<ReleaseYear>>(trailer, type)
    }

    @TypeConverter
    fun fromStringToPoster(value: String?): Poster? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Poster>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPosterToString(value: Poster?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToRating(value: String?): Rating? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Rating>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromRatingToString(value: Rating?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToVotes(value: String?): Votes? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Votes>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromVotesToString(value: Votes?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromGenresListToString(genres: List<Genres?>?): String? {
        if (genres == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genres?>?>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun fromStringToGenresList(genres: String?): List<Genres>? {
        if (genres == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genres?>?>() {}.type
        return gson.fromJson<List<Genres>>(genres, type)
    }

    @TypeConverter
    fun fromPersonListToString(persons: List<Person?>?): String? {
        if (persons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Person?>?>() {}.type
        return gson.toJson(persons, type)
    }

    @TypeConverter
    fun fromStringToPersonList(persons: String?): List<Person>? {
        if (persons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Person?>?>() {}.type
        return gson.fromJson<List<Person>>(persons, type)
    }

    @TypeConverter
    fun fromVideosList(trailer: List<Trailer?>?): String? {
        if (trailer == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Trailer?>?>() {}.type
        return gson.toJson(trailer, type)
    }

    @TypeConverter
    fun toVideosList(trailer: String?): List<Trailer>? {
        if (trailer == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Trailer?>?>() {}.type
        return gson.fromJson<List<Trailer>>(trailer, type)
    }
}
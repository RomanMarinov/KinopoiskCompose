package com.dev_marinov.kinopoiskapp.data

import androidx.room.TypeConverter
import com.dev_marinov.kinopoiskapp.domain.model.Genre
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.Videos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//@ProvidedTypeConverter
class DataConvertersForList {

        @TypeConverter
        fun fromVideos(videos: Videos): String? {
                if (videos == null) {
                        return null
                }
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
        fun fromListString(value: List<String>?): String? {
                return value?.joinToString(",")
        }

        @TypeConverter
        fun toListString(value: String?): List<String>? {
                return value?.split(",")?.map { it.toString() }
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

        @TypeConverter
        fun fromGenresList(genres: List<Genre?>?): String? {
                if (genres == null) {
                        return null
                }
                val gson = Gson()
                val type: Type = object : TypeToken<List<Genre?>?>() {}.type
                return gson.toJson(genres, type)
        }

        @TypeConverter
        fun toGenresList(genre: String?): List<Genre>? {
                if (genre == null) {
                        return null
                }
                val gson = Gson()
                val type: Type = object : TypeToken<List<Genre?>?>() {}.type
                return gson.fromJson<List<Genre>>(genre, type)
        }


//        @TypeConverter
//        fun fromString(name: String?): List<String?>? {
//                val listType = object : TypeToken<List<String?>?>() {}.type
//                return Gson().fromJson(name, listType)
//        }
//
//        @TypeConverter
//        fun fromArrayList(name: List<String?>?): String? {
//                val gson = Gson()
//                return gson.toJson(name)
//        }

//        @TypeConverter
//        fun convertFromStringList(name: List<String>): String = name.joinToString(",")
//
//        @TypeConverter
//        fun convertToStringList(name: String): List<String> = name.split(",").toList()

}
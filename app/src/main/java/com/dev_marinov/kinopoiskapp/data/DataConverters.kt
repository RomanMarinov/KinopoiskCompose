package com.dev_marinov.kinopoiskapp.data

import androidx.room.TypeConverter
import com.dev_marinov.kinopoiskapp.domain.model.Genre
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverters {

//        @TypeConverter
//        fun fromVideosList(trailer: List<Trailer?>?): String? {
//            if (trailer == null) {
//                return null
//            }
//            val gson = Gson()
//            val type: Type = object : TypeToken<List<Trailer?>?>() {}.type
//            return gson.toJson(trailer, type)
//        }
//
//        @TypeConverter
//        fun toVideosList(trailer: String?): List<Trailer>? {
//            if (trailer == null) {
//                return null
//            }
//            val gson = Gson()
//            val type: Type = object : TypeToken<List<Trailer?>?>() {}.type
//            return gson.fromJson<List<Trailer>>(trailer, type)
//        }


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



    //  class StringListConverter {
        @TypeConverter
        fun convertFromStringList(name: List<String>): String = name.joinToString(",")

        @TypeConverter
        fun convertToStringList(name: String): List<String> = name.split(",").toList()
 //   }

}
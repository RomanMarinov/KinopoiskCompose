package com.dev_marinov.kinopoiskapp.data.video.remote

import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.google.gson.annotations.SerializedName

data class TrailerDTO(
   // @SerializedName("_id")
    //val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String
) {
    fun mapToDomain(movieId: Int): Trailer {
        return Trailer(
           // id = id,
            url = url,
            name = name,
            site = site,
            movieId = movieId
        )
    }
}


//data class TrailerDTO(
//    val url: String,
//    val name: String
//) {
//    fun mapToDomain(): Trailer {
//        return Trailer(
//            url = url,
//            name = name
//        )
//    }
//}
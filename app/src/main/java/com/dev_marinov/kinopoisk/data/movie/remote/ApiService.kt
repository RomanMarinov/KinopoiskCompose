package com.dev_marinov.kinopoisk.data.movie.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val default_token: String = "H6FVA5Q-0BW47S8-GSX42CA-32G17EW"
private const val defaultPage: String = "1"
private const val defaultLimit: String = "20"

interface ApiService {
    // https://kinopoisk.dev/documentation.html // документация


    //https://api.kinopoisk.dev/movie?field=rating.kp&search=7-10&field=year&search=2017-2020&field=typeNumber&search=2&sortField=year&sortType=1&sortField=votes.imdb&sortType=-1&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06

    // с моим токеном
    //https://api.kinopoisk.dev/movie?field=rating.kp&search=7-10&field=year&search=2017-2020&field=typeNumber&search=2&sortField=year&sortType=1&sortField=votes.imdb&sortType=-1&token=H6FVA5Q-0BW47S8-GSX42CA-32G17EW

    //    TODO: uncomment search params
    @GET("movie")
    suspend fun getData(
//        @Query("field") fieldRatingKp: String = "rating.kp",
//        @Query("search") searchRating: String,
//        @Query("field") fieldDate: String = "year",
//        @Query("search") search_date: String,
//        @Query("field") field_typeNumber: String = "typeNumber",
//        @Query("search") search_typeNumber: String,
//        @Query("sortField") sortField_date: String = "year",
//        @Query("sortType") sortType_date: String,
//        @Query("sortField") sortField_rating_imdb: String = "votes.imdb",
//        @Query("sortType") sortType_rating: String,
        @Query("token") token: String = default_token,
        @Query("page") page: String = defaultPage,
        @Query("limit") limit: String = defaultLimit
    ): Response<GetMoviesResponse>
}
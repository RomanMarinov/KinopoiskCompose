package com.dev_marinov.kinopoiskapp.data.movie.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private  val default_fieldRatingKp: String = "rating.kp"
private  val default_searchRating: String = "7-10"
private  val default_fieldDate: String = "year"
private  val default_search_date: String = "2017-2020"
private  val default_field_typeNumber: String = "typeNumber"
private  val default_search_typeNumber: String = "2"
private  val default_sortField_date: String = "year"
private  val default_sortType_date: String = "1"
private  val default_sortField_rating_imdb: String = "votes.imdb"
private  val default_sortType_rating: String = "-1"
private  val default_token: String = "H6FVA5Q-0BW47S8-GSX42CA-32G17EW"
private  val defaultPage: String = "1"
private  val defaultLimit: String = "3"

interface ApiService {
    // https://kinopoisk.dev/documentation.html // документация
    //https://api.kinopoisk.dev/movie?field=rating.kp&search=7-10&field=year&search=2017-2020&field=typeNumber&search=2&sortField=year&sortType=1&sortField=votes.imdb&sortType=-1&token=H6FVA5Q-0BW47S8-GSX42CA-32G17EW

    @GET("movie")
    suspend fun getData(
        @Query("field") fieldRatingKp: String = default_fieldRatingKp,
        @Query("search") searchRating: String = default_searchRating,
        @Query("field") fieldDate: String = default_fieldDate,
        @Query("search") search_date: String = default_search_date,
        @Query("field") field_typeNumber: String = default_field_typeNumber,
        @Query("search") search_typeNumber: String = default_search_typeNumber,
        @Query("sortField") sortField_date: String = default_sortField_date,
        @Query("sortType") sortType_date: String = default_sortType_date,
        @Query("sortField") sortField_rating_imdb: String = default_sortField_rating_imdb,
        @Query("sortType") sortType_rating: String = default_sortType_rating,
        @Query("token") token: String = default_token,
        @Query("page") page: String = defaultPage,
        @Query("limit") limit: String = defaultLimit
    ): Response<GetMoviesResponse>
}
package com.dev_marinov.kinopoiskapp.data.movie.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // https://api.kinopoisk.dev/v1.3/movie?
    // selectFields=poster&
    // selectFields=rating&
    // selectFields=genres&
    // selectFields=externalId&
    // selectFields=movieLength&
    // selectFields=persons&
    // selectFields=videos.trailers&
    // selectFields=votes&
    // selectFields=id&
    // selectFields=type&
    // selectFields=description&
    // selectFields=year&
    // selectFields=releaseYears&
    // selectFields=name&

    // type=cartoon&
    // field=rating.kp&
    // field=year&
    // search=6-10&
    // search=2017-2020&
    // videos.trailers=%21null&
    // token=H6FVA5Q-0BW47S8-GSX42CA-32G17EW&
    // page=1&
    // limit=20

    @GET("v1.3/movie")
    suspend fun getData(
        @Query("selectFields") selectFields: List<String>,
        @Query("type") type: String,
        @Query("field") field: List<String>,
        @Query("search") search: List<String>,
        @Query("videos.trailers") videosTrailers: String,
        @Query("token") token: String = "H6FVA5Q-0BW47S8-GSX42CA-32G17EW",
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<GetMoviesResponse>
}
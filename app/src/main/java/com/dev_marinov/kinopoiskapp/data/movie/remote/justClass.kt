package com.dev_marinov.kinopoiskapp.data.movie.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//private val default_poster: String = "poster"
//private val default_rating: String = "rating"
//private val default_genres: String = "genres"
//private val default_externalId: String = "externalId"
////private val default_persons: String = "persons.name"
//private val default_movieLength: String = "movieLength"
//private val default_persons: String = "persons"
//private val default_videos: String = "videos.trailers"
//
//private val default_votes: String = "votes"
//private val default_id: String = "id"
//private val default_type: String = "type"
//private val default_description: String = "description"
//private val default_year: String = "year"
//private val default_releaseYears: String = "releaseYears"
//
//private val default_name: String = "name"
//private  val default_fieldRatingKp: String = "rating.kp"
//private  val default_searchRating: String = "7-10"
//private  val default_fieldDate: String = "year"
//private  val default_search_date: String = "2017-2020"
////private  val default_field_typeNumber: String = "typeNumber" // для числа жанра
//private  val default_field_typeNumber: String = "type" // для строка жанра
//
//private  val default_search_typeNumber: String = "2"  // для числа жанра
//
//
//private  val default_sortField_date: String = "year"
//private  val default_sortType_date: String = "1"
//private  val default_sortField_rating_imdb: String = "votes.imdb"
//private  val default_sortType_rating: String = "-1"
//private  val defaultPage: String = "1"
//private  val defaultLimit: String = "20"
//
//private  val default_token: String = "H6FVA5Q-0BW47S8-GSX42CA-32G17EW"
//private val default_videos_not_null = "!null"
//
//interface ApiService {
//
//    @GET("v1.3/movie")
//    suspend fun getData(
//        @Query("selectFields") poster: String = default_poster,
//        @Query("selectFields") rating: String = default_rating,
//        @Query("selectFields") genres: String = default_genres,
//        @Query("selectFields") externalId: String = default_externalId,
//        @Query("selectFields") movieLength: String = default_movieLength,
//        @Query("selectFields") person: String = default_persons,
//        @Query("selectFields") videos: String = default_videos,
//
//        @Query("selectFields") votes: String = default_votes,
//        @Query("selectFields") id: String = default_id,
//        @Query("selectFields") type: String = default_type,
//        @Query("selectFields") description: String = default_description,
//        @Query("selectFields") year: String = default_year,
//        @Query("selectFields") releaseYears: String = default_releaseYears,
//
//        @Query("selectFields") name: String = default_name,
//        @Query("field") fieldRatingKp: String = default_fieldRatingKp,
////        @Query("search") searchRating: String = default_searchRating, //
//        @Query("search") searchRating: String? = null, //
//        @Query("field") fieldDate: String = default_fieldDate,
//        //  @Query("search") search_date: String = default_search_date, //
//        @Query("search") search_date: String? = null, //
//        @Query("field") field_typeNumber: String = default_field_typeNumber,
//
////        @Query("search") search_typeNumber: String = default_search_typeNumber, // было число
//        @Query("search") search_type: String? = null,
//        @Query("sortField") sortField_date: String = default_sortField_date,
//        @Query("sortType") sortType_date: String = default_sortType_date,
//        @Query("sortField") sortField_rating_imdb: String = default_sortField_rating_imdb,
//        @Query("sortType") sortType_rating: String = default_sortType_rating,
//        @Query("token") token: String = default_token,
//
//        @Query("page") page: String = defaultPage,
//        @Query("limit") limit: String = defaultLimit,
//
//        @Query("videos.trailers") videos_not_null: String = default_videos_not_null,
//        // @Query("persons.name") persons_not_null: String = default_videos_not_null,
//    ): Response<GetMoviesResponse>
//}
package com.dev_marinov.kinopoiskapp.data.video

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dev_marinov.kinopoiskapp.data.video.local.VideosDao
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.Videos
import com.dev_marinov.kinopoiskapp.domain.repository.VideosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepositoryImpl @Inject constructor(private val videosDao: VideosDao) : VideosRepository {

//    override val videos: LiveData<List<Videos>> = videosDao.getAllFlow().map {
//        it.map { videosEntity ->
//            Log.d("4444"," videosEntity=" + videosEntity)
//            videosEntity.mapToDomain()
//        }
//    }

    override val videos: Flow<List<Videos>> = videosDao.getAllFlow().map {

        Log.d("4444", " VideosRepositoryImpl it" + it)
        it.map { videosEntity ->
            videosEntity.mapToDomain()

        }
    }

    override suspend fun getTrailersForDetail(movieId: Int): List<Trailer>? {
        return videosDao.getTrailers(movie_id = movieId).trailers
    }
}
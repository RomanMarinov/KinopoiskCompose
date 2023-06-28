package com.dev_marinov.kinopoiskapp.data.video.local


import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.dev_marinov.kinopoiskapp.data.DataConvertersForList
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.Trailer
import com.dev_marinov.kinopoiskapp.domain.model.Videos

@Entity(
    tableName = "videos",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = CASCADE
        )
    ]
)

@TypeConverters(DataConvertersForList::class)
data class VideosEntity(
   // @PrimaryKey(autoGenerate = false)
    //val id: Int?,
    val trailers: List<Trailer>?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
) {
    companion object {
        fun mapFromDomain(videos: Videos): VideosEntity {
            return VideosEntity(
                //id = videos.id,
                trailers = videos.trailers,
                movieId = videos.movieId
            )
        }
    }

    fun mapToDomain(): Videos {
        return Videos(
           // id = id,
            trailers = trailers,
            movieId = movieId
        )
    }
}
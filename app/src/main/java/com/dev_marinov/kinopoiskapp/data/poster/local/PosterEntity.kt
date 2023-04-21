package com.dev_marinov.kinopoiskapp.data.poster.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.domain.model.Poster

@Entity(
    tableName = "posters",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"], // связь род таб с табл постер по айди (в муви айди и постер айди)
            childColumns = ["movie_id"], //
            onDelete = ForeignKey.CASCADE // когда удаляем фильм, то удаляем и постер
        // если я сам удалю фильм и все елементы тоже удаляться
        )
    ]
)
data class PosterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    val url: String,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
) {
    companion object {
 // когда доменную модель надо перенести в энтити
        fun mapFromDomain(poster: Poster): PosterEntity = PosterEntity(
            id = poster.id,
            previewUrl = poster.previewUrl,
            url = poster.url,
            movieId = poster.movieId
        )
    }

    fun mapToDomain(): Poster = Poster(
        id = id,
        previewUrl = previewUrl,
        url = url,
        movieId = movieId
    )
}
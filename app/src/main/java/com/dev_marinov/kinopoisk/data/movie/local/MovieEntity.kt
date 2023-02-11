package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dev_marinov.kinopoisk.data.StringListConverter
import com.dev_marinov.kinopoisk.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "alternative_name")
    val alternativeName: String,
    val description: String?,
    val length: Int?,
    val name: String?,
    @ColumnInfo(name = "short_description")
    val shortDescription: String?,
    val type: String?,
    val year: Int?,
    @ColumnInfo(name = "release_years_ids")
    @TypeConverters(StringListConverter::class)
    val releaseYearsIds: List<String>?,
    @ColumnInfo(name = "votes_id")
    val votesId: String?,
    @ColumnInfo(name = "rating_id")
    val ratingId: String?,
    @ColumnInfo(name = "poster_id")
    val posterId: String?,
    val page: Int
) {

    companion object {
        fun mapFromDomain(movie: Movie): MovieEntity = MovieEntity(
            alternativeName = movie.alternativeName,
            description = movie.description,
            id = movie.id,
            length = movie.length,
            name = movie.name,
            shortDescription = movie.shortDescription,
            type = movie.type,
            year = movie.year,
            releaseYearsIds = movie.releaseYearsIds,
            votesId = movie.votesId,
            ratingId = movie.ratingId,
            posterId = movie.posterId,
            page = movie.page
        )
    }

    fun mapToDomain(): Movie = Movie(
        alternativeName = alternativeName,
        description = description,
        id = id,
        length = length,
        name = name,
        shortDescription = shortDescription,
        type = type,
        year = year,
        releaseYearsIds = releaseYearsIds,
        votesId = votesId,
        ratingId = ratingId,
        posterId = posterId,
        page = page
    )
}

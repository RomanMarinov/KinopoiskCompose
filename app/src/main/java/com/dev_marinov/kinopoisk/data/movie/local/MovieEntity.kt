package com.dev_marinov.kinopoisk.data.movie.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dev_marinov.kinopoisk.data.StringListConverter
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear
import com.dev_marinov.kinopoisk.domain.model.Votes

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
    val releaseYearsIds: List<ReleaseYear>?,
    @ColumnInfo(name = "votes_id")
    val votesId: Votes?,
    @ColumnInfo(name = "rating_id")
    val ratingId: Rating?,
    @ColumnInfo(name = "poster_id")
    val posterId: Poster?,
    val page: Int
)

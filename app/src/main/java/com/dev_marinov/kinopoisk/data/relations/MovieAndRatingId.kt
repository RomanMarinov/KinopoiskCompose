package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.poster.local.PosterEntity
import com.dev_marinov.kinopoisk.data.rating.local.RatingEntity


// вспомогательный класс, который связывает таблицы Movie и Rating
// это простые отношения one to one
data class MovieAndRatingId(
    // рум поймет и включить все области которые есть в школе после написания @Embedded
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        // родительский столбец, который сылается на родительский столбец
        parentColumn = "id",
        // столбец, который не является превичным ключом, но сравнивается со столбцом "  ",
        // который является епрвичным ключом
        entityColumn = "rating_id"
    )
    // не комментируя его @Embedded мы говорим руму что ratingEntity это объект наших отношений
    val ratingEntity: RatingEntity
)
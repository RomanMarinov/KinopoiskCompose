package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.rating.local.RatingEntity
import com.dev_marinov.kinopoisk.data.votes.local.VotesEntity


// это вспомогательный класс имеет доступ к студенту, который посещает список занятий
data class MovieWithRatingIds(
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "rating_id",
        // сообщаем руму какая таблица определяет это отношение
        associateBy = Junction(MovieRatingIdCrossRef::class)
    )
    val subjects: List<RatingEntity>
)

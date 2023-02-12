package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.poster.local.PosterEntity
import com.dev_marinov.kinopoisk.data.votes.local.VotesEntity


// это вспомогательный класс имеет доступ к фильму, который посещает список занятий
data class MovieWithPosterIds(
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "poster_id",
        // сообщаем руму какая таблица определяет это отношение
        associateBy = Junction(MoviePosterIdCrossRef::class)
    )
    val subjects: List<PosterEntity>
)

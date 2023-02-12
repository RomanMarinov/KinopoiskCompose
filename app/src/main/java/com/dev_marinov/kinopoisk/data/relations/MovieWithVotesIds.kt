package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.votes.local.VotesEntity


// это вспомогательный класс имеет доступ к студенту, который посещает список занятий
data class MovieWithVotesIds(
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "votes_id",
        // сообщаем руму какая таблица определяет это отношение
        associateBy = Junction(MovieVotesIdCrossRef::class)
    )
    val subjects: List<VotesEntity>
)

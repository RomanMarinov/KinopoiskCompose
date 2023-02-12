package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.dev_marinov.kinopoisk.data.movie.local.MovieEntity
import com.dev_marinov.kinopoisk.data.poster.local.PosterEntity
import com.dev_marinov.kinopoisk.data.votes.local.VotesEntity


// вспомогательный класс, который связывает таблицы Movie и Votes
// это простые отношения one to one
data class MovieAndVotesId(
    // рум поймет и включить все области которые есть в школе после написания @Embedded
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        // родительский столбец, который сылается на родительский столбец
        parentColumn = "id",
        // столбец, который не является превичным ключом, но сравнивается со столбцом "  ",
        // который является епрвичным ключом
        entityColumn = "votes_id"
    )
    // не комментируя его @Embedded мы говорим руму что votesEntity это объект наших отношений
    val votesEntity: VotesEntity
)
package com.dev_marinov.kinopoisk.data.relations

import androidx.room.Entity


// вспомогательный класс, который связывает таблицы MovieEntity и PosterEntity
// это отношения n to m
@Entity (primaryKeys = ["id", "poster_id"]) // два поля это первичный ключ
data class MoviePosterIdCrossRef( // перекрестная ссылка
    val movieId: String,
    val posterId: String
)
package com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite

import com.dev_marinov.kinopoiskapp.domain.model.movie_combine.MovieItemCombine

data class SelectableFavoriteMovie(
    val movie: MovieItemCombine,
    val isFavorite: Boolean
)

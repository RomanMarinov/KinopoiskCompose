package com.dev_marinov.kinopoiskapp.presentation.model

import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem

data class SelectableFavoriteMovie(
    val movie: MovieItem,
    val isFavorite: Boolean
)

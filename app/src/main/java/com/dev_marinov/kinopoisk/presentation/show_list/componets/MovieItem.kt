package com.dev_marinov.kinopoisk.presentation.show_list.componets

import com.dev_marinov.kinopoisk.domain.model.Movie
import com.dev_marinov.kinopoisk.domain.model.Poster
import com.dev_marinov.kinopoisk.domain.model.Rating
import com.dev_marinov.kinopoisk.domain.model.ReleaseYear

class MovieItem(
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val rating: Rating?,
    val poster: Poster?
)
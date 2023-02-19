package com.dev_marinov.kinopoiskapp.presentation.home

import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.model.Poster
import com.dev_marinov.kinopoiskapp.domain.model.Rating
import com.dev_marinov.kinopoiskapp.domain.model.ReleaseYear

class MovieItem(
    val movie: Movie,
    val releaseYears: List<ReleaseYear>,
    val rating: Rating?,
    val poster: Poster?
)
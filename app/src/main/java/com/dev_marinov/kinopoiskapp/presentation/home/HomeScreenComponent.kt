package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val movieItems: List<MovieItem> by viewModel.movieItems.collectAsState(listOf())

    Column(modifier = Modifier.fillMaxSize()) {
        TotBar()
        Movies(movieItems, viewModel)
    }
}

@Composable
fun Movies(movieItems: List<MovieItem>, viewModel: HomeViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home")

        LazyColumn(
            content = {
                itemsIndexed(movieItems) { index, item ->
                    MovieItem(item, viewModel::onMovieClicked)
                }
            },
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        )
    }
}

@Composable
fun MovieItem(
    movieItem: MovieItem,
    onClick: (MovieItem) -> Unit /*clickListener for item*/
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(movieItem) }
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.LightGray, shape = RectangleShape)
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movieItem.poster?.previewUrl,
                contentDescription = "Movie poster",
                placeholder = painterResource(id = R.drawable.id_poster_placehoolder)
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "name: ${movieItem.movie.name}")
                Text(text = "rating: ${movieItem.rating?.kp}")
                Text(text = "years: ${movieItem.releaseYears.joinToString(",") { "${it.start} - ${it.end}" }}")
            }
        }
    }
}
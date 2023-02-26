package com.dev_marinov.kinopoiskapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.detail.DetailScreen
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val movieItems: List<MovieItem> by viewModel.movieItems.collectAsState(listOf())
    Column(modifier = Modifier.fillMaxWidth()) {
        TotBar()
        Movies(movieItems, viewModel, navController)
    }
}

@Composable
fun Movies(movieItems: List<MovieItem>, viewModel: HomeViewModel, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home")
        LazyColumn(
            content = {
                itemsIndexed(movieItems) { index, item ->
                    MovieItem(item, viewModel::onMovieClicked, navController)
                }
            },
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        )
    }
    Log.d("4444", " movies")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieItem(
    movieItem: MovieItem,
    onClick: (MovieItem) -> Unit /*clickListener for item*/,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate(Screen.DetailScreen.withArgs(movieItem.movie.name))
            },
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.LightGray, shape = RectangleShape)
                .padding(10.dp)
                .fillMaxWidth(),
        ) {
            AsyncImage(
                model = movieItem.poster?.previewUrl,
                contentDescription = "Movie poster",
                placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "name: ${movieItem.movie.name}")
                Text(text = "rating: ${movieItem.rating?.kp}")
                Text(text = "years: ${movieItem.releaseYears.joinToString(",") { "${it.start} - ${it.end}" }}")
            }
        }
    }
}
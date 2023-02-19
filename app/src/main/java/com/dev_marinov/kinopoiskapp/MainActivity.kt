package com.dev_marinov.kinopoiskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.presentation.home.ListViewModel
import com.dev_marinov.kinopoiskapp.presentation.home.MovieItem
import com.dev_marinov.kinopoiskapp.ui.theme.KinopoiskAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KinopoiskAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun MainScreen(viewModel: ListViewModel = hiltViewModel()) {
    val movieItems: List<MovieItem> by viewModel.movieItems.collectAsState(listOf())

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
                placeholder = painterResource(
                    id = R.drawable.id_poster_placehoolder
                )
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "name: ${movieItem.movie.name}")
                Text(text = "rating: ${movieItem.rating?.kp}")
                Text(text = "years: ${movieItem.releaseYears.joinToString(",") { "${it.start} - ${it.end}" }}")
            }
        }
    }
}

package com.dev_marinov.kinopoiskapp.presentation.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
fun DetailScreen(
    movieId: String?,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    movieId?.let { viewModel.getMovie(it) }
    val movie by viewModel.movie.observeAsState()
    //SetViews(movieItem = movie)
    Log.d("4444", " movie zzz=" + movie)
}

@Composable
fun SetViews(movieItem: MovieItem) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        //.background(colorResource(id = R.color.my_green)) // как установить свой цвет
        //.padding(horizontal = 30.dp)
    ) {
        // поле для центрирования текста
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray, shape = RectangleShape)
                    .padding(5.dp)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = movieItem.poster?.url,
                    contentDescription = "Movie poster",
                    placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
                )

//                Column() {
//
//                    Text(text = "name: ${movieItem.movie.name}")
//                    Text(text = "rating: ${movieItem.rating?.kp}")
//                    Text(text = "years: ${movieItem.releaseYears
//                        .joinToString(",") { "${it.start} - ${it.end}" }}")
//                }
            }
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .padding(15.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(color = Color.Gray)
//                    .padding(horizontal = 15.dp, vertical = 20.dp)
//                    .fillMaxWidth()
//            ) {
//                Column() {
//                    Text(
//                        text = "Daily Thought",
//                        style = MaterialTheme.typography.h2
//                    )
//                    Text(
//                        text = "Meditation  -  3 - 10 min",
//                        style = MaterialTheme.typography.body1,
//                        color = Color.White
//                    )
//                }
//            }
        }
    }
}

@Composable
fun MyFun() {
    Box(
        modifier = Modifier
            .fillMaxWidth()) {
        Column() {
            Text(
                text = "Daily Thought",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Meditation  -  3 - 10 min",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }
}
package com.dev_marinov.kinopoiskapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    isOnHome: Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScrollPosition = remember {
        mutableStateOf(0)
    }
    val movie by viewModel.movie.collectAsStateWithLifecycle(initialValue = emptyList())
    val isHide by viewModel.isHide.collectAsStateWithLifecycle(initialValue = true)

    val getVideos by viewModel.getVideos().collectAsStateWithLifecycle(listOf())
    Log.d("4444"," getVideos=" + getVideos)

    //val result by viewModel.result.observeAsState()
    //val result by viewModel.result.collectAsStateWithLifecycle(initialValue = emptyList())
    //val result = viewModel.result


   // Log.d("4444", " HomeScreen movie=" + movie)

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.topBottomBarHide(isHide = isOnHome)
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPreFling(available: Velocity): Velocity {
                viewModel.saveScroll(currentScrollPosition)
                return super.onPreFling(available)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(isHide = isHide)
        Movies(movie, viewModel, navController, nestedScrollConnection, currentScrollPosition)
    }
}

@Composable
fun Movies(
    movieItems: List<MovieItem>,
    viewModel: HomeViewModel,
    navController: NavController,
    nestedScrollConnection: NestedScrollConnection,
    currentScrollPosition: MutableState<Int>,
) {

    //val res = movieItems[1].videos?.trailers

//    for (item in 0..movieItems.size) {
//        Log.d("4444", " item=" + movieItems[item].videos)
//    }


    //Log.d("4444", " screen loaded")




    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        
        

        
        Text(text = "home")
        LazyVerticalGrid(
            content = {
                itemsIndexed(movieItems) { index, item ->
                    MovieItem(item, viewModel::onMovieClickedHideBar, navController)
                    currentScrollPosition.value = index


                    // Log.d("4444", " index=" + index)
                }
            },
            columns = GridCells.Adaptive(150.dp),
            //contentPadding = PaddingValues((10.dp)),
            //verticalArrangement = Arrangement.spacedBy(10.dp)
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .nestedScroll(nestedScrollConnection),
            // verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        )
    }
}

@Composable
fun MovieItem(
    movieItem: MovieItem,
    onMovieClickedHideBar: (Boolean) -> Unit, /*clickListener for item*/
    navController: NavController,
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onMovieClickedHideBar.invoke(true)
                navController.navigate(Screen.DetailScreen.withArgs(movieItem.movie.id))
            }
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
            .background(Color.Black)
        ) {

            Box(modifier = Modifier
                .wrapContentSize()
                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 40.dp)
            ) {   // контейнер в котором будет уложено друг на друга

                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    model = movieItem.poster?.previewUrl,
                    contentDescription = "Movie poster",
                    alignment = Alignment.Center,
                    placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
                )
            }

            Box( // градиент
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), // от прозрачного к черному
                            startY = 500f
                        )
                    )
            )

            Box( // year
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd) {
                Text(text = "2017", style = TextStyle(color = Color.White, fontSize = 16.sp))
            }

            Box( // kp
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart) {
                Text(
                    text = movieItem.rating?.kp.toString().substring(0, 3),
                    style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}
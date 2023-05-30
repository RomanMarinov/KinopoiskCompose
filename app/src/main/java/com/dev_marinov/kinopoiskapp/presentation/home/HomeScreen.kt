@file:OptIn(ExperimentalMaterialApi::class)

package com.dev_marinov.kinopoiskapp.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.model.MovieItem
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
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
    val movie by viewModel.movie.collectAsState(listOf())
    val isHideTopBar by viewModel.isHideTopBar.collectAsStateWithLifecycle(initialValue = true)
    val selectChipIndex by viewModel.selectChipIndex.collectAsStateWithLifecycle()
    val selectGenres by viewModel.selectGenres.collectAsStateWithLifecycle()
    //val isShowBottomSheet by viewModel.isShowBottomSheet.collectAsStateWithLifecycle(false)

    Log.d("4444", " movie=" + movie)
////////////
//    val uploadData by viewModel.uploadData.observeAsState()
//    if (uploadData == 0) {
//        viewModel.onClickedShowBottomSheet()
//    }
//
        ///////////

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.topBottomBarHide(isHide = false) // показать бар навигации
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

    val color1 = ContextCompat.getColor(LocalContext.current, R.color.color1)
    val color2 = ContextCompat.getColor(LocalContext.current, R.color.color2)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(color1),
                        Color(color2)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(Color.Transparent)
        ) {
            TopBar(
                isHide = isHideTopBar,
               // isVisibleBottomSheet = isVisibleBottomSheet,
                viewModel = viewModel
            )
            Movies(
                movie,
                viewModel,
                navController,
                nestedScrollConnection,
                currentScrollPosition,
                selectChipIndex,
                selectGenres
            )
        }
    }
}

@Composable
fun Movies(
    movieItems: List<MovieItem>,
    viewModel: HomeViewModel,
    navController: NavController,
    nestedScrollConnection: NestedScrollConnection,
    currentScrollPosition: MutableState<Int>,
    selectChipIndex: Int,
    selectGenres: String
) {

    if (movieItems.isNotEmpty()) {
//        if (movieItems[0].movie.type == selectGenres.lowercase()) {
//            Log.d("4444", " movieItems=" + movieItems)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    content = {
                        itemsIndexed(movieItems) { index, item ->
                            MovieItem(
                                item,
                                viewModel::onMovieClickedHideNavigationBar,
                                navController,
                                selectChipIndex,
                                viewModel
                            )
                            currentScrollPosition.value = index
                        }
                    },
                    columns = GridCells.Adaptive(150.dp),
                    //contentPadding = PaddingValues((10.dp)),
                    //verticalArrangement = Arrangement.spacedBy(10.dp)
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .nestedScroll(nestedScrollConnection),
                    // verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                )
            }
        //}
    }
}

@Composable
fun MovieItem(
    movieItem: MovieItem,
    onMovieClickedHideBar: (Boolean) -> Unit, /*clickListener for item*/
    navController: NavController,
    selectChipIndex: Int,
    viewModel: HomeViewModel
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(350.dp)
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 60.dp)
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    model = movieItem.poster?.previewUrl,
                    contentDescription = "Movie poster",
                    alignment = Alignment.Center,
                    placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), // от прозрачного к черному
                            startY = 400f
                        )
                    )
            )

            Box( // kp
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp, 30.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                if (selectChipIndex == 0) {
                    Text(
                        text = movieItem.rating?.kp.toString().substring(0, 3),
                        style = TextStyle(
                            color = Color.Yellow, fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                } else {
                    Text(
                        text = movieItem.rating?.kp.toString().substring(0, 3),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }
            }

            Box( // year
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp, 30.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (selectChipIndex == 1) {
                    Text(
                        text = movieItem.movie.year.toString(),
                        style = TextStyle(color = Color.Yellow, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = movieItem.movie.year.toString(),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }
            }

            Box( // name
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                movieItem.movie.name?.let {
                    var movieName = ""
                    val indexMax = it.length
                    if (indexMax >= 16) {
                        movieName = it.substring(0, 16).plus("...")
                    }
                    if (indexMax < 16) {
                        movieName = it
                    }
                    if (selectChipIndex == 2) {
                        Text(
                            text = movieName,
                            style = TextStyle(
                                color = Color.Yellow,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    } else {
                        Text(
                            text = movieName,
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}
package com.dev_marinov.kinopoiskapp.presentation.favorite

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.SetShimmer
import com.dev_marinov.kinopoiskapp.presentation.home.StartSnackBar
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import com.dev_marinov.kinopoiskapp.presentation.model.SelectableFavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val connectivity by viewModel.connectivity.collectAsStateWithLifecycle(initialValue = ConnectivityObserver.Status.UnAvailable)

    val favoriteMovies by viewModel.favoriteMovies.observeAsState()
    val gradientColorApp by viewModel.getGradientColorApp.collectAsState(listOf())

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.topBottomBarHide(isHide = false) // показать бар навигации
        }
    }

    when (connectivity) {
        ConnectivityObserver.Status.UnAvailable -> {
            StartSnackBar("internet connection unavailable")
            SetShimmer()
        }
        ConnectivityObserver.Status.Available -> {
            // StartSnackBar("internet connection available")
            if (gradientColorApp.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = gradientColorApp,
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
                        favoriteMovies?.let {
                            FavoriteMovies(
                                it,
                                viewModel,
                                navController
                            )
                        }
                    }
                }
            }
        }
        ConnectivityObserver.Status.Losing -> {
            StartSnackBar("internet connection losing")
            SetShimmer()
        }
        ConnectivityObserver.Status.Lost -> {
            StartSnackBar("internet connection lost")
            SetShimmer()
        }
    }
}


@Composable
fun FavoriteMovies(
    movieItems: List<SelectableFavoriteMovie>,
    viewModel: FavoriteScreenViewModel,
    navController: NavController,
//    nestedScrollConnection: NestedScrollConnection,
//    currentScrollPosition: MutableState<Int>,
 //   selectChipIndex: Int,
//    selectGenres: String
) {

    if (movieItems.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            LazyVerticalGrid(
                content = {
                    itemsIndexed(movieItems) { index, item ->
                        FavoriteMovieItem(
                            item,
                            viewModel::onMovieClickedHideNavigationBar,
                            navController,
     //                       selectChipIndex,
                            viewModel
                        )
                        //  currentScrollPosition.value = index
                    }
                },
                columns = GridCells.Adaptive(150.dp),
                //contentPadding = PaddingValues((10.dp)),
                //verticalArrangement = Arrangement.spacedBy(10.dp)
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                // .nestedScroll(nestedScrollConnection),
                ,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            )
        }
        //}
    }
}

@Composable
fun FavoriteMovieItem(
    movieItem: SelectableFavoriteMovie,
    onMovieClickedHideBar: (Boolean) -> Unit, /*clickListener for item*/
    navController: NavController,
   // selectChipIndex: Int,
    viewModel: FavoriteScreenViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                 onMovieClickedHideBar.invoke(true)
                navController.navigate(Screen.DetailScreen.withArgs(movieItem.movie.movie.id))
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
                    model = movieItem.movie.poster?.previewUrl,
                    contentDescription = "Movie poster",
                    alignment = Alignment.Center,
                    placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
                )

                ////////////
                Box(
                    modifier = Modifier
                        .offset(x = (150).dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(30))
                        .background(Color.Transparent)
                        .clickable {


                            viewModel.onClickFavorite(
                                SelectableFavoriteMovie(
                                    movie = movieItem.movie,
                                    isFavorite = false
                                )
                            )
                        },
                    contentAlignment = Alignment.TopEnd,

                    ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30))
                            .size(50.dp)
                            .background(
                                colorResource(id = R.color.backgroundFavorite),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(34.dp),
                            painter = painterResource(
                                if (movieItem.isFavorite) R.drawable.ic_star_on
                                else R.drawable.ic_star_off
                            ),
                            contentDescription = "Icon star"
                        )
                    }
                }
                /////////////////////

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
//                if (selectChipIndex == 0) {
//                    Text(
//                        text = movieItem.movie.rating?.kp.toString().substring(0, 3),
//                        style = TextStyle(
//                            color = Color.Yellow, fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                } else {
                    Text(
                        text = movieItem.movie.rating?.kp.toString().substring(0, 3),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
            //    }
            }

            Box( // year
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp, 30.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
//                if (selectChipIndex == 1) {
//                    Text(
//                        text = movieItem.movie.movie.year.toString(),
//                        style = TextStyle(color = Color.Yellow, fontSize = 16.sp),
//                        fontWeight = FontWeight.Bold
//                    )
//                } else {
                    Text(
                        text = movieItem.movie.movie.year.toString(),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
               // }
            }

            Box( // name
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                movieItem.movie.movie.name?.let {
                    var movieName = ""
                    val indexMax = it.length
                    if (indexMax >= 16) {
                        movieName = it.substring(0, 16).plus("...")
                    }
                    if (indexMax < 16) {
                        movieName = it
                    }
//                    if (selectChipIndex == 2) {
//                        Text(
//                            text = movieName,
//                            style = TextStyle(
//                                color = Color.Yellow,
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        )
//                    } else {
                        Text(
                            text = movieName,
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )
                   // }
                }
            }
        }
    }
}

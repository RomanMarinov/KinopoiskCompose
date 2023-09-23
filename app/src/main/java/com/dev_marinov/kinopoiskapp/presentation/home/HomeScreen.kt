package com.dev_marinov.kinopoiskapp.presentation.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import com.dev_marinov.kinopoiskapp.domain.model.pagination.PagingParams
import com.dev_marinov.kinopoiskapp.domain.model.selectable_favorite.SelectableFavoriteMovie
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val connectivity by viewModel.connectivity.collectAsStateWithLifecycle(initialValue = ConnectivityObserver.Status.UnAvailable)

    val currentScrollPosition = remember {
        mutableStateOf(0)
    }

    val isHideTopBar by viewModel.isHideTopBar.collectAsStateWithLifecycle(initialValue = true)
    val selectChipIndex by viewModel.selectChipIndex.collectAsStateWithLifecycle()
    val isPlayingLottie by viewModel.isPlayingLottie.collectAsState(initial = false)
    val gradientColorApp by viewModel.getGradientColorApp.collectAsState(listOf())
    val getPagingParams by viewModel.pagingParams.collectAsStateWithLifecycle()

    val movies by viewModel.newFavoriteMovies.collectAsStateWithLifecycle(initialValue = listOf())

    val responseCodeAllMovies by viewModel.responseCodeAllMovies.collectAsStateWithLifecycle()

    if (responseCodeAllMovies == 403) {
        Toast.makeText(
            context,
            stringResource(id = R.string.movie_requests_exceeded_200_times),
            Toast.LENGTH_LONG
        ).show()
    }

    if (isPlayingLottie && movies.isEmpty()) {
        LottieExample(isPlaying = true)
    } else {
        LottieExample(isPlaying = false)
        viewModel.isPlayingLottie(isPlaying = false)
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.topBottomBarHide(isHide = false)
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

                        TopBar(
                            isHide = isHideTopBar,
                            viewModel = viewModel
                        )
                        Movies(
                            movies,
                            viewModel,
                            navController,
                            nestedScrollConnection,
                            currentScrollPosition,
                            selectChipIndex,
                            getPagingParams
                        )
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
fun SetShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
            .background(Color.Gray)
    ) {}
}

@Composable
fun Movies(
    movieItems: List<SelectableFavoriteMovie>,
    viewModel: HomeViewModel,
    navController: NavController,
    nestedScrollConnection: NestedScrollConnection,
    currentScrollPosition: MutableState<Int>,
    selectChipIndex: Int,
    pagingParams: PagingParams?,
) {
    var num = 15

    val moviesSize = remember { mutableStateOf(0) }
    val gridState = rememberLazyGridState()
    val lastVisibleIndexState = remember { mutableStateOf<Int?>(null) }

    if (num == lastVisibleIndexState.value) { // lastVisibleIndexState должен срабатывать на 15 -> 35 -> 55 -> 75 -> 95
        num += 20
        // новый запрос + 20 элементов
        // здесь я должен получать объект с параметрами по типу genre в котором делаю скролл
//        viewModel.getParams(selectGenres = selectGenres)
        pagingParams?.let {
            // viewModel.page = it.page + 1
            viewModel.getData(pagingParams = it)
        }
    }

    LaunchedEffect(gridState) { // LaunchedEffect перезаупскается когда меняется значение gridState
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo } // получает информацию о видимых элементах из gridState
            .distinctUntilChanged() // фильтрует поток данных, чтобы обрабатывать только уникальные последовательности значений.
            .collectLatest { visibleItems -> // сбор данных из потока
                val lastVisibleIndex = visibleItems.lastOrNull()?.index
                if (lastVisibleIndex != lastVisibleIndexState.value) {
                    lastVisibleIndexState.value = lastVisibleIndex
                }
            }
    }

    if (movieItems.isNotEmpty()) {
        moviesSize.value = movieItems.size

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
                // contentPadding = PaddingValues((10.dp)),
                // verticalArrangement = Arrangement.spacedBy(10.dp)
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .nestedScroll(nestedScrollConnection),
                // verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState,
            )
        }
    }
}

@Composable
fun MovieItem(
    movieItem: SelectableFavoriteMovie,
    onMovieClickedHideBar: (Boolean) -> Unit, /*clickListener for item*/
    navController: NavController,
    selectChipIndex: Int,
    viewModel: HomeViewModel,
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

                // //////////
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .offset(x = (150).dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(30))
                        .background(Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (movieItem.isFavorite) {
                                    viewModel.onClickFavorite(
                                        SelectableFavoriteMovie(
                                            movie = movieItem.movie,
                                            isFavorite = false
                                        )
                                    )
                                } else {
                                    viewModel.onClickFavorite(
                                        SelectableFavoriteMovie(
                                            movie = movieItem.movie,
                                            isFavorite = true
                                        )
                                    )
                                }
                            }
                        ),
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
                            // painter = painterResource(id = R.drawable.ic_star_off),
                            painter = painterResource(
                                if (movieItem.isFavorite) R.drawable.ic_star_on
                                else R.drawable.ic_star_off
                            ),
                            contentDescription = "Icon star"
                        )
                    }
                }
                // ///////////////////
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
                        text = movieItem.movie.rating?.kp.toString().substring(0, 3),
                        style = TextStyle(
                            color = Color.Yellow, fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                } else {
                    Text(
                        text = movieItem.movie.rating?.kp.toString().substring(0, 3),
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
                        text = movieItem.movie.movie.year.toString(),
                        style = TextStyle(color = Color.Yellow, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = movieItem.movie.movie.year.toString(),
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

                movieItem.movie

                movieItem.movie.movie.name?.let {
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

@Composable
fun LottieExample(isPlaying: Boolean) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.progress_lottie_animation))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever, // бесконечно
        isPlaying = isPlaying, // пауза/воспроизведение
        speed = 2.0f,
        restartOnPlay = false // передать false, чтобы продолжить анимацию на котором он был приостановлен
    )

    if (isPlaying) {
        IsLottieAnimationVisibility(
            composition = composition,
            progress = progress,
            visibility = 1.0f
        )
    } else {
        IsLottieAnimationVisibility(
            composition = composition,
            progress = progress,
            visibility = 0.0f
        )
    }
}

@Composable
fun IsLottieAnimationVisibility(
    composition: LottieComposition?,
    progress: Float,
    visibility: Float
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(100.dp)
                .alpha(visibility)
        )
    }
}

@Composable
fun StartSnackBar(status: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Snackbar(
                modifier = Modifier.fillMaxWidth(),
                action = { /* Handle action */ }
            ) {
                Text(text = status)
            }
        }
    }
}
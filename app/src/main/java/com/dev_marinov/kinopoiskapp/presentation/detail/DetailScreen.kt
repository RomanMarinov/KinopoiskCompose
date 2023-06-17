package com.dev_marinov.kinopoiskapp.presentation.detail

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.domain.model.Genres
import com.dev_marinov.kinopoiskapp.presentation.detail.model.MovieItemDetail
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen


@Composable
fun DetailScreen(
    movieId: String?,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController
) {
    movieId?.let { viewModel.getMovie(it) }
    val movieItemDetail by viewModel.movie.observeAsState()
    SetViews(movieItemDetail = movieItemDetail, navController = navController)
}

@Composable
fun SetViews(
    movieItemDetail: MovieItemDetail?,
    navController: NavController
) {


    var imageHeight by remember {
        mutableStateOf(0)
    }
    val heightPoster = with(LocalDensity.current) { imageHeight.toDp() }

    val listState = rememberLazyListState()

    var imageSize by remember {
        mutableStateOf(600.dp)
    }

    val size by animateDpAsState(
        targetValue = imageSize,
        // tween делаетанимацию с задержкой
        //  tween(durationMillis = 3000, delayMillis = 300, easing = LinearOutSlowInEasing), // прмени эту
        //  spring(Spring.DampingRatioHighBouncy) // или примени эту
        tween(durationMillis = 400)
    )

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemScrollOffset
        }
            .collect {
                if (it != 0 && it.dp < 350.dp) {
                    val value = 600.dp
                    imageSize = value - it.dp
                }
                if (it == 0) {
                    imageSize = 600.dp
                }
            }
    }

    Column(
        //verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
        //.height(300.dp)
        //.background(colorResource(id = R.color.my_green)) // как установить свой цвет
        //.padding(horizontal = 30.dp)
    ) {
        // поле для центрирования текста

        Log.d("4444", " detail movieItemDetail?.poster?.url=" + movieItemDetail?.poster?.url)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            AsyncImage(
                modifier = Modifier
                    .statusBarsPadding()
                    .systemBarsPadding()
                    .onSizeChanged { size ->
                        imageHeight = size.height
                    }
                    .size(size),
                model = movieItemDetail?.poster?.url,
                contentDescription = "Movie poster",
                placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
            )
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .statusBarsPadding()
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        item {
            DescriptionBlock(
                movieItemDetail = movieItemDetail,
                heightPoster = heightPoster,
                navController = navController
            )
        }
    }
}

@Composable
fun DescriptionBlock(
    movieItemDetail: MovieItemDetail?,
    heightPoster: Dp,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        movieItemDetail?.let { movieItemDetail ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightPoster - 30.dp)
                    .background(Color.Transparent)
            )
            // val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    //  .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(start = 4.dp, end = 4.dp)
            ) {
                movieItemDetail.movie.name?.let {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val matrix = ColorMatrix()
                        matrix.setToSaturation(0F)
                        Image(
                            painter = painterResource(id = R.drawable.ic_line),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(40.dp)
                                //.clip(CircleShape)
                                .background(color = Color.White)
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        // style = typography.h4,
                        text = it,
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "категория: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.movie.type}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                val genres: String = getToString(movieItemDetail.genres)

                Text(
                    text = "жанр: ",
                    fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                    fontSize = 16.sp
                )

                Text(
                    text = genres,
                    fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                    fontSize = 20.sp,
                    style = TextStyle(fontWeight = Bold)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "премьера: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.movie.year}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "продолжительность: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.movie.movieLength}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = " минут",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "дата начала: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.releaseYear?.start}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "дата окончания: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.releaseYear?.end}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "рейтинг kp: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.rating?.kp}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = " голоса: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.vote?.kp}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "рейтинг imdb: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.rating?.imdb}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = " голоса: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.vote?.imdb}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "рейтинг ожидания: ",
                        fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                        fontSize = 16.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "${movieItemDetail.rating?.await}",
                        fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = Bold),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "описание: ",
                    fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${movieItemDetail.movie.description}",
                    fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                    fontSize = 20.sp,
                    style = TextStyle(fontWeight = Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Log.d("4444", " movieItemDetail.movie.id="+    movieItemDetail.movie.id)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_play_video),
                        contentDescription = "contentDescription",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(70.dp)
                            .clickable(onClick = {
                                navController.navigate(
                                    Screen.PlayVideoScreen.withArgs(
                                        movieItemDetail.movie.id
                                    )
                                )
                            })
                    )

//                movieItemDetail.videos?.let {
//                   // VideoViewNew("https://youtu.be/poUq9ypynKs")
//                }
                    //https://youtu.be/poUq9ypynKs
//                movieItemDetail.videos?.let {
//                    VideoViewNew(videoUri = it[3].url)
//                    Log.d("4444", "url=" + it[3].url)
//                }


                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "актеры, режиссеры, съемочная группа: ",
                    fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(movieItemDetail.persons) { index, item ->
                        Card(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(150.dp),
                            elevation = 6.dp
                        )
                        {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    model = item.photo,
                                    contentDescription = "Movie person",
                                    placeholder = painterResource(id = R.drawable.id_poster_placehoolder),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (item.name != null) {
                                    SetNamePerson(name = item.name)
                                } else {
                                    SetNamePerson(name = "неизвестно")
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SetNamePerson(name: String) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .height(45.dp),
        text = name,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

fun getToString(items: List<*>): String {
    if (items.any { it is Genres }) {
        val genres: List<Genres> = items.filterIsInstance<Genres>()
        return genres.joinToString { genre ->
            "${genre.genres}"
        }
            .replace(", null,", ",")
            .replace(", null", "")
    }
    return ""
}
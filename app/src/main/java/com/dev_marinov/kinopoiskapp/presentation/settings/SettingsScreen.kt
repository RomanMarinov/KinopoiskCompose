package com.dev_marinov.kinopoiskapp.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev_marinov.kinopoiskapp.ConnectivityObserver
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.home.SetShimmer
import com.dev_marinov.kinopoiskapp.presentation.home.StartSnackBar

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val connectivity by viewModel.connectivity.collectAsStateWithLifecycle(initialValue = ConnectivityObserver.Status.UnAvailable)

    val gradientColorsApp by viewModel.gradientColorsApp.collectAsState(listOf())
    val gradientColorApp by viewModel.getGradientColorApp.collectAsState(listOf())
    val gradientColorIndexApp by viewModel.getGradientColorIndexApp.collectAsState(initial = 0)

    val getCountStatAll by viewModel.getCountStatAll.collectAsState(initial = 0)
    val getCountStatMovies by viewModel.getCountStatMovies.collectAsState(initial = 0)
    val getCountStatTvSeries by viewModel.getCountStatTvSeries.collectAsState(initial = 0)
    val getCountStatCartoon by viewModel.getCountStatCartoon.collectAsState(initial = 0)
    val getCountStatAnime by viewModel.getCountStatAnime.collectAsState(initial = 0)
    val getCountStatAnimatedSeries by viewModel.getCountStatAnimatedSeries.collectAsState(initial = 0)
    val countFavorite = viewModel.countFavorite.collectAsStateWithLifecycle(initialValue = 0)

    when (connectivity) {
        ConnectivityObserver.Status.UnAvailable -> {
            StartSnackBar("internet connection unavailable")
            SetShimmer()
        }
        ConnectivityObserver.Status.Available -> {
            // StartSnackBar("internet connection available")
            if (gradientColorApp.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = gradientColorApp,
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY // наибольшее возможное значение
                            )
                        ),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BoxList(
                        viewModel = viewModel,
                        gradientColorsApp = gradientColorsApp,
                        gradientColorIndexApp = gradientColorIndexApp
                    )
                    BoxStatistic(
                        getCountStatAll = getCountStatAll,
                        getCountStatMovies = getCountStatMovies,
                        getCountStatTvSeries = getCountStatTvSeries,
                        getCountStatCartoon = getCountStatCartoon,
                        getCountStatAnime = getCountStatAnime,
                        getCountStatAnimatedSeries = getCountStatAnimatedSeries,
                        countFavorite = countFavorite.value
                    )
                    BoxButton(gradientColorApp = gradientColorApp, viewModel = viewModel)
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
fun BoxList(
    viewModel: SettingsScreenViewModel,
    gradientColorsApp: List<List<Color>>,
    gradientColorIndexApp: Int
) {
    val boxes = listOf("Box 1", "Box 2", "Box 3")
    var selectedBoxIndex by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (gradientColorsApp.isNotEmpty()) {
            for (i in boxes.indices) {
                val borderColor = if (gradientColorIndexApp == i) Color.Black else Color.Transparent
                val backgroundColor: Brush = when (i) {
                    0 -> Brush.verticalGradient(gradientColorsApp[0])
                    1 -> Brush.verticalGradient(gradientColorsApp[1])
                    2 -> Brush.verticalGradient(gradientColorsApp[2])
                    else -> SolidColor(Color.Transparent)
                }
                Box(
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(backgroundColor)
                        .border(
                            BorderStroke(2.dp, borderColor),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(onClick = {
                            selectedBoxIndex = i
                            viewModel.setGradientColorApp(selectedBoxIndex = selectedBoxIndex)
                        })
                        .size(
                            if (selectedBoxIndex == i) 100.dp else 98.dp,
                            if (selectedBoxIndex == i) 250.dp else 248.dp
                        )
                ) {

                }
            }
        }
    }
}

@Composable
fun BoxStatistic(
    getCountStatAll: Int,
    getCountStatMovies: Int,
    getCountStatTvSeries: Int,
    getCountStatCartoon: Int,
    getCountStatAnime: Int,
    getCountStatAnimatedSeries: Int,
    countFavorite: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "all - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatAll.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "movies - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatMovies.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "tv-series - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatTvSeries.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "cartoon - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatCartoon.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "anime - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatAnime.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "animated-series - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = getCountStatAnimatedSeries.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
            Text(
                text = "favorites - ",
                fontFamily = FontFamily(Font(R.font.robotoserif_28pt_black)),
                fontSize = 16.sp,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = countFavorite.toString(),
                fontFamily = FontFamily(Font(R.font.opensans_lightltalic)),
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.alignByBaseline()
            )
        }
    }


}

@Composable
fun BoxButton(
    gradientColorApp: List<Color>,
    viewModel: SettingsScreenViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        MyDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                // тут результат клика на ок
                viewModel.onClearMoviesClicked()
                // закрыть
                showDialog = false
            },
            gradientColorApp = gradientColorApp
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { showDialog = true

                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Clear all movies",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun MyDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    gradientColorApp: List<Color>
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)


                .clip(RoundedCornerShape(24.dp))
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColorApp,
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY // наибольшее возможное значение
                        )
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = R.string.clear_all_movies),
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
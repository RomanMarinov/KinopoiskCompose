package com.dev_marinov.kinopoiskapp.presentation.activity

import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev_marinov.kinopoiskapp.R
import com.dev_marinov.kinopoiskapp.presentation.activity.model.BottomNavItem
import com.dev_marinov.kinopoiskapp.presentation.detail.DetailScreen
import com.dev_marinov.kinopoiskapp.presentation.favorite.FavoriteScreen
import com.dev_marinov.kinopoiskapp.presentation.home.HomeScreen
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import com.dev_marinov.kinopoiskapp.presentation.play_video.PlayVideoScreen
import com.dev_marinov.kinopoiskapp.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavigation(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val countSelectGenre = viewModel.countSelectGenre.collectAsStateWithLifecycle(initialValue = 0)

    Log.d("4444", " countSelectGenre=" + countSelectGenre.value)

    val countMovies = viewModel.countMovies.collectAsStateWithLifecycle(initialValue = 0)
    val isHideBottomBar by viewModel.isHideBottomBar.collectAsStateWithLifecycle(initialValue = true)
    val clickedFilter by viewModel.clickedFilter.collectAsStateWithLifecycle(initialValue = false)
    val clickedTypeGenre by viewModel.clickedTypeGenre.collectAsStateWithLifecycle(initialValue = "")

    val color1 = ContextCompat.getColor(LocalContext.current, R.color.color1)
    val color2 = ContextCompat.getColor(LocalContext.current, R.color.color2)
    val gradientColors = listOf(Color(color1), Color(color2))

    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val genresBottomSheet = remember {
        context.resources.getStringArray(R.array.genresBottomSheet)
    }

    val yearPickerFrom by viewModel.yearPickerFrom.collectAsStateWithLifecycle(2000)
    val yearPickerTo by viewModel.yearPickerTo.collectAsStateWithLifecycle(2000)
    val yearPickerStateFrom = remember { mutableStateOf(yearPickerFrom) }
    val yearPickerStateTo = remember { mutableStateOf(yearPickerTo) }

    val ratingPickerFrom by viewModel.ratingPickerFrom.collectAsStateWithLifecycle(0)
    val ratingPickerTo by viewModel.ratingPickerTo.collectAsStateWithLifecycle(10)
    val ratingPickerStateFrom = remember { mutableStateOf(ratingPickerFrom) }
    val ratingPickerStateTo = remember { mutableStateOf(ratingPickerTo) }

    // Конвертация массива в список
    val genres = remember { genresBottomSheet.toList() }
    var selectedGenreBottomSheet by remember { mutableStateOf(genres[0]) }


        // если убрать systemBarsPadding то щит будет под
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxHeight(),
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors,
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY // наибольшее возможное значение
                        )
                    )
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        val matrix = ColorMatrix()
                        matrix.setToSaturation(0F)
                        Image(
                            painter = painterResource(id = R.drawable.ic_line),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "years",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AndroidView(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(150.dp)
                                        .padding(top = 16.dp, end = 8.dp),
                                    factory = { context ->
                                        NumberPicker(context).apply {
                                            setOnValueChangedListener { numberPicker, oldValue, newValue ->
                                                // Сохраняем выбранное значение в состояние
                                                yearPickerStateFrom.value = newValue
                                                viewModel.selectedYearPickerFrom(yearPickerStateFrom.value)
                                                value = yearPickerStateFrom.value
                                            }
                                            minValue = 1988
                                            maxValue = 2023
                                        }
                                    }
                                )

                                AndroidView(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(150.dp)
                                        .padding(top = 16.dp, end = 8.dp),
                                    factory = { context ->
                                        NumberPicker(context).apply {
                                            setOnValueChangedListener { numberPicker, oldValue, newValue ->
                                                // Сохраняем выбранное значение в состояние
                                                yearPickerStateTo.value = newValue
                                                viewModel.selectedYearPickerTo(yearPickerStateTo.value)
                                                value = yearPickerStateTo.value
                                            }
                                            minValue = 1988
                                            maxValue = 2023
                                        }
                                    }
                                )
                            }

                            //    }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "ratings kp",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AndroidView(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(150.dp)
                                        .padding(top = 16.dp, end = 8.dp),
                                    factory = { context ->
                                        NumberPicker(context).apply {
                                            setOnValueChangedListener { numberPicker, oldValue, newValue ->
                                                // Сохраняем выбранное значение в состояние
                                                ratingPickerStateFrom.value = newValue
                                                viewModel.selectedRatingPickerFrom(
                                                    ratingPickerStateFrom.value
                                                )
                                                value = ratingPickerStateFrom.value
                                            }
                                            minValue = 0
                                            maxValue = 10
                                        }
                                    }
                                )
                                AndroidView(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(150.dp)
                                        .padding(top = 16.dp, end = 8.dp),
                                    factory = { context ->
                                        NumberPicker(context).apply {
                                            setOnValueChangedListener { numberPicker, oldValue, newValue ->
                                                // Сохраняем выбранное значение в состояние
                                                ratingPickerStateTo.value = newValue
                                                viewModel.selectedRatingPickerTo(ratingPickerStateTo.value)
                                                value = ratingPickerStateTo.value
                                            }
                                            minValue = 0
                                            maxValue = 10
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {


                        Button(
                            modifier = Modifier
                                .fillMaxWidth().systemBarsPadding(),
                               // .height(60.dp),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                viewModel.bottomSheetParams(
                                    yearPickerFrom = yearPickerFrom,
                                    yearPickerTo = yearPickerTo,
                                    ratingPickerFrom = ratingPickerFrom,
                                    ratingPickerTo = ratingPickerTo,
                                    genre = clickedTypeGenre
                                )
                                coroutineScope.launch { modalSheetState.hide() }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Choose",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }

    ) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier
                        .animateContentSize(animationSpec = tween(durationMillis = 800))
                        .height(height = if (isHideBottomBar == true) 0.dp else 70.dp),
                    //.height(70.dp),
                    items = listOf(
                        BottomNavItem(
                            name = "Home",
                            route = "home",
                            icon = Icons.Default.Home,
                            badgeCount = countSelectGenre.value
                        ),
                        BottomNavItem(
                            name = "Favorite",
                            route = "favorite",
                            icon = Icons.Default.Favorite,
                            badgeCount = 23
                        ),
                        BottomNavItem(
                            name = "Settings",
                            route = "settings",
                            icon = Icons.Default.Settings,
                            badgeCount = 214
                        ),
                    ),
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route)
                    },
                    gradientColors = gradientColors
                )
            }

        ) { paddingValues ->
            // передаем падинг чтобы список BottomNavigationBar не накладывался по поверх списка
            Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                NavigationGraph(navHostController = navController)
            }
        }
    }

    LaunchedEffect(clickedFilter) {
        modalSheetState.let {
            if (clickedFilter) {
                it.show()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    gradientColors: List<Color>,
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        backgroundColor = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        elevation = 60.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors,
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY // наибольшее возможное значение
                    )
                ),
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.DarkGray,
                unselectedContentColor = Color.White,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                modifier = Modifier.size(30.dp),
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = Screen.HomeScreen.route) {
            val isOnHome =
                navHostController.currentBackStackEntryAsState().value?.destination?.route == "home"
            HomeScreen(navController = navHostController, isOnHome)
        }
        composable(
            // если бы было несколько аргументов то передавал бы один за другим "/{name}/{age}"
            // если мы вдруг не передадим стровое значение что запись будет такая "?name={name}"
            route = Screen.DetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType // тип передаваемого значения строка
                    defaultValue = "Manmario"
                    nullable = true // можно обнулить
                }
            )
        ) { entry -> // запись
            DetailScreen( // получатель
                movieId = entry.arguments?.getString("id"),
                navController = navHostController
            )
        }
        composable(
            // если бы было несколько аргументов то передавал бы один за другим "/{name}/{age}"
            // если мы вдруг не передадим стровое значение что запись будет такая "?name={name}"
            route = Screen.PlayVideoScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType // тип передаваемого значения строка
                    defaultValue = "Manmario"
                    nullable = true // можно обнулить
                }
            )
        ) { entry -> // запись
            PlayVideoScreen(
                // получатель
                movieId = entry.arguments?.getString("id"),
                // navController = navHostController
            )
        }
        composable(route = Screen.FavoriteScreen.route) { FavoriteScreen() }
        composable(route = Screen.SettingsScreen.route) { SettingsScreen() }
    }
}


package com.dev_marinov.kinopoiskapp.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev_marinov.kinopoiskapp.presentation.activity.model.BottomNavItem
import com.dev_marinov.kinopoiskapp.presentation.favorite.FavoriteScreen
import com.dev_marinov.kinopoiskapp.presentation.home.HomeScreen
import com.dev_marinov.kinopoiskapp.presentation.settings.SettingsScreen
import com.dev_marinov.kinopoiskapp.ui.theme.KinopoiskAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KinopoiskAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home,
                                    badgeCount = 0
                                ),
                                BottomNavItem(
                                    name = "Favorite",
                                    route = "favorite",
                                    icon = Icons.Default.Notifications,
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
                            }
                        )
                    }
                )
                {
                    Navigation(navHostController = navController)
                }
            }
        }
    }
}


//@Composable
//private fun MainScreen(viewModel: HomeViewModel = hiltViewModel()) {
//    val movieItems: List<MovieItem> by viewModel.movieItems.collectAsState(listOf())
//
//    LazyColumn(
//        content = {
//            itemsIndexed(movieItems) { index, item ->
//                MovieItem(item, viewModel::onMovieClicked)
//            }
//        },
//        contentPadding = PaddingValues(10.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp)
//    )
//
//}
//
//@Composable
//fun MovieItem(
//    movieItem: MovieItem,
//    onClick: (MovieItem) -> Unit /*clickListener for item*/
//) {
//    Card(
//        modifier = Modifier
//            .wrapContentSize()
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick(movieItem) }
//    ) {
//        Row(
//            modifier = Modifier
//                .background(color = Color.LightGray, shape = RectangleShape)
//                .padding(10.dp)
//                .fillMaxWidth()
//        ) {
//            AsyncImage(
//                model = movieItem.poster?.previewUrl,
//                contentDescription = "Movie poster",
//                placeholder = painterResource(id = R.drawable.id_poster_placehoolder)
//            )
//            Column(modifier = Modifier.padding(10.dp)) {
//                Text(text = "name: ${movieItem.movie.name}")
//                Text(text = "rating: ${movieItem.rating?.kp}")
//                Text(text = "years: ${movieItem.releaseYears.joinToString(",") { "${it.start} - ${it.end}" }}")
//            }
//        }
//    }
//}

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("favorite") { FavoriteScreen() }
        composable("settings") { SettingsScreen() }
    }
}


@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
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

package com.dev_marinov.kinopoiskapp.presentation.activity

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev_marinov.kinopoiskapp.presentation.activity.model.BottomNavItem
import com.dev_marinov.kinopoiskapp.presentation.detail.DetailScreen
import com.dev_marinov.kinopoiskapp.presentation.favorite.FavoriteScreen
import com.dev_marinov.kinopoiskapp.presentation.home.HomeScreen
import com.dev_marinov.kinopoiskapp.presentation.home.util.Screen
import com.dev_marinov.kinopoiskapp.presentation.settings.SettingsScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigation(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val countMovies = viewModel.countMovies.collectAsStateWithLifecycle(initialValue = 0)
    val isHide by viewModel.isHide.collectAsStateWithLifecycle(initialValue = true)

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 600))
                    .height(height = if (isHide == true) 0.dp else 70.dp),
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Default.Home,
                        badgeCount = countMovies.value + 1
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
                }
            )
        }

    ) { paddingValues ->
        // передаем падинг чтобы список BottomNavigationBar не накладывался по поверх спика
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            NavigationGraph(navHostController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)),
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
            val isOnHome = navHostController.currentBackStackEntryAsState().value?.destination?.route == "home"
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
                movieId = entry.arguments?.getString("id")
            )
        }
        composable(route = Screen.FavoriteScreen.route) { FavoriteScreen() }
        composable(route = Screen.SettingsScreen.route) { SettingsScreen() }
    }
}

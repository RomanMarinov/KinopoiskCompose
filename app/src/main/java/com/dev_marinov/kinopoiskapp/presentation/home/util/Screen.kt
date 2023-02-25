package com.dev_marinov.kinopoiskapp.presentation.home.util

// класс который использует маршрут route
// поэтому каждому экрану нужен этот маршрут
// а экраны это объекты которым не нужны параметры
sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object DetailScreen : Screen("detail")

    // функция будет работать только с обязательными аргументами
    fun withArgs(vararg args: String?) : String {
        // возвращаем блок построителя строки
        return buildString {
            append(route) // хотим добавлять всегда общий маршрут экрана
            args.forEach { // перебрать аргументы
                append("/$it")
            }
        }
    }
}

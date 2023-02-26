package com.dev_marinov.kinopoiskapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun DetailScreen(name: String?) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.background(colorResource(id = R.color.my_green)) // как установить свой цвет
            .padding(horizontal = 30.dp)
    ) {
        // поле для центрирования текста
        Box(
            contentAlignment = Alignment.Center, // выравнивание по центру
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text(
                text = "Hello, $name",
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
}
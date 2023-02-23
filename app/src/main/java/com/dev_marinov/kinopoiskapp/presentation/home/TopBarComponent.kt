package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev_marinov.kinopoiskapp.R

@Composable
fun TotBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        ChipSection(
            chips = listOf("Rating", "Year", "Abc")
        )
        FilterButton()
    }
}

@Composable
fun FilterButton(
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier
            .size(40.dp),
        backgroundColor = Color.Yellow,
        onClick = {

        }) {
        Image(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = null,
            modifier = modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(color = Color.Yellow)
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>,
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    // ленивая строка которая прокручивается и загружает составные элементы которые поместим в нее
    LazyRow {
        items(chips.size) { // кол-во элементов которое соответсвует размеру списка
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .border( // граница для этой кнопки
                        width = 1.dp,
                        color = Color.Gray,
                        // закругленная угловая форма
                        shape = RoundedCornerShape(50.dp) //
                    )
                    .clip(RoundedCornerShape(50.dp)) // обрезаем поле применим закругленные углы
                    .clickable { // делаем поле кликабельным
                        selectedChipIndex = it
                    }
                    .background(
                        if (selectedChipIndex == it) Color.LightGray // светлый выбранный
                        else Color.White // темный не выбранный
                    )
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)

            ) {
                Text(
                    text = chips[it],
                    fontWeight = if (selectedChipIndex == it) FontWeight.SemiBold // светлый выбранный
                    else FontWeight.Normal, // полужирный
                    color = Color.Black
                )
            }
        }
    }
}
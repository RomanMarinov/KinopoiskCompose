package com.dev_marinov.kinopoiskapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet() {
    // тут состояние нижнего листа
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed // изначально свернут
    )
    // тут состояние каркаса
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope =
        rememberCoroutineScope() // без карутин sheetState.expand() не вызовиться
    BottomSheetScaffold( // оболочка
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Bottom sheet", fontSize = 60.sp)
            }
        },

        // мы можем указать множество параметров, чтобы изменить поведение нижнего листа
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp // начальное значение позиции, но может и не ноль
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // в коробке все по центру
        ) {
            Button(onClick = {
                scope.launch {
                    // условие когда мы хотим свернуть и обратно
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }

                }
            }) {
                Text(
                    // ${sheetState.progress.fraction нужно для привязки каких либо других
                    // действий к прогрессу перемещения шторки
                    text = "Bottom sheet fraction ${sheetState.progress.fraction}"
                )
            }
        }
    }
}
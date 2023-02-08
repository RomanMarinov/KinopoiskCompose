package com.dev_marinov.kinopoisk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.dev_marinov.kinopoisk.domain.model.Doc
import com.dev_marinov.kinopoisk.presentation.show_list.componets.ListViewModel
import com.dev_marinov.kinopoisk.ui.theme.KinopoiskTheme
import dagger.hilt.android.AndroidEntryPoint

// H6FVA5Q-0BW47S8-GSX42CA-32G17EW
// https://kinopoisk.dev/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KinopoiskTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun MainScreen(viewModel: ListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val viewState: List<Doc> by viewModel.viewState.collectAsState()



}

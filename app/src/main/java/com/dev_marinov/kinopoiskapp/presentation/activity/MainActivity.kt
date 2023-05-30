package com.dev_marinov.kinopoiskapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.core.view.WindowCompat
import com.dev_marinov.kinopoiskapp.ui.theme.KinopoiskAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            KinopoiskAppTheme {
                StatusBarColor()
                NavigationBarColor()
                BottomNavigation()
            }
        }
    }
}

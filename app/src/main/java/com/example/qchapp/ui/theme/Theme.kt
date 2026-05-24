package com.example.qchapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(

    primary = QCHGreen,
    secondary = QCHOrange,
    tertiary = QCHGray,

    background = BGGray,
    surface = BGGray,

    onPrimary = QCHWhite,
    onBackground = QCHBlack
)

private val LightColorScheme = lightColorScheme(

    primary = QCHGreen,
    secondary = QCHOrange,
    tertiary = QCHGray,

    background = BGGray,
    surface = QCHWhite,

    onPrimary = QCHWhite,
    onBackground = QCHBlack
)

@Composable
fun QCHAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
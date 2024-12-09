package com.asmaa.khb.foodmenuapp.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = WhitePrimary,
    inversePrimary = WhitePrimary,
    secondary = DarkPurpleSecondary,
    tertiary = DarkGreenTertiary,
    background = WhitePrimary,
    surface = WhiteBackground,
    onPrimary = BlackOnPrimary,
    onSecondary = WhiteText,
    onTertiary = WhiteText,
    onBackground = DarkGrayOnBackground,
    onSurface = GrayOnSurface,
    error = RedSelection,
    errorContainer = RedSelectionBackground,
    onError = WhiteText
)

@Composable
fun FoodicsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme, /* for now only support white theme */
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
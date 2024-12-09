package com.asmaa.khb.foodmenuapp.presentation.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontSize = xlargeTextSize,
        fontWeight = FontWeight.Bold,
        color = BlackOnPrimary
    ),

    titleMedium = TextStyle(
        fontSize = mediumTextSize,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = smallTextSize,
        color = DarkGrayOnBackground
    ),
    /* smallest text */
    titleSmall = TextStyle(
        fontSize = xsmallTextSize,
        fontWeight = FontWeight.Bold,
        color = BlackOnPrimary
    ),
    bodySmall = TextStyle(
        fontSize = xsmallTextSize,
        fontWeight = FontWeight.Light,
        color = GrayOnSurface
    ),
    labelSmall = TextStyle(
        fontSize = xsmallTextSize,
        fontWeight = FontWeight.Light,
        color = BlackOnPrimary
    )
)
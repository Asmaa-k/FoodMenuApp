package com.asmaa.khb.foodmenuapp.presentation.ui.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.asmaa.khb.foodmenuapp.R

data class BottomNavObject(@StringRes val title: Int, val icon: ImageVector, val route: String)

val navigationItems = listOf(
    BottomNavObject(
        R.string.bottom_nav_label_tables,
        Icons.Default.Restaurant,
        RoutesConstants.TABLE_SCREEN_ROUTE
    ),
    BottomNavObject(
        R.string.bottom_nav_label_orders,
        Icons.Default.MenuBook,
        RoutesConstants.ORDERS_SCREEN_ROUTE
    ),
    BottomNavObject(
        R.string.bottom_nav_label_menu,
        Icons.Default.LocalDining,
        RoutesConstants.MENU_SCREEN_ROUTE
    ), BottomNavObject(
        R.string.bottom_nav_label_settings,
        Icons.Default.Settings,
        RoutesConstants.SETTINGS_SCREEN_ROUTE
    )
)
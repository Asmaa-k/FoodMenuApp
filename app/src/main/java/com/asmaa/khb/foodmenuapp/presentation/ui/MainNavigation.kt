package com.asmaa.khb.foodmenuapp.presentation.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asmaa.khb.foodmenuapp.presentation.ui.screens.ScreenPlaceHolder
import com.asmaa.khb.foodmenuapp.presentation.ui.screens.ScreenTable
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.RoutesConstants


@Composable
fun MainNavigation(modifier: Modifier, navController: NavHostController) {
    NavHost(navController = navController, startDestination = RoutesConstants.TABLE_SCREEN_ROUTE) {
        composable(RoutesConstants.TABLE_SCREEN_ROUTE) { ScreenTable(modifier) }
        composable(RoutesConstants.ORDERS_SCREEN_ROUTE) { ScreenPlaceHolder(modifier) }
        composable(RoutesConstants.MENU_SCREEN_ROUTE) { ScreenPlaceHolder(modifier) }
        composable(RoutesConstants.SETTINGS_SCREEN_ROUTE) { ScreenPlaceHolder(modifier) }
    }
}
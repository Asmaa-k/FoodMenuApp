package com.asmaa.khb.foodmenuapp.presentation.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.WhitePrimary
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.navigationItems

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = { navigate(navController, item.route) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.icon.toString(),
                            modifier = Modifier.size(xsmallIconSize),
                        )
                        Spacer(modifier = Modifier.height(xsmallPadding))
                        Text(
                            text = stringResource(id = item.title),
                            maxLines = 1,
                            style = if (currentDestination?.route == item.route) {
                                MaterialTheme.typography.labelSmall
                            } else {
                                MaterialTheme.typography.bodySmall
                            },
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = WhitePrimary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }
    }
}

fun navigate(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

package com.asmaa.khb.foodmenuapp.presentation.ui.screens

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.asmaa.khb.foodmenuapp.presentation.ui.MainNavigation
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.FoodicsAppTheme
import com.asmaa.khb.foodmenuapp.presentation.ui.views.AppToolbar
import com.asmaa.khb.foodmenuapp.presentation.ui.views.BottomNavBar
import com.asmaa.khb.foodmenuapp.presentation.ui.views.LockScreenOrientation
import com.asmaa.khb.foodmenuapp.presentation.ui.views.TransparentStatusBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodicsAppTheme {
                TransparentStatusBar()
                LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = { AppToolbar() },
        bottomBar = { BottomNavBar(navController) },
        content = {
            MainNavigation(Modifier.padding(it), navController)
        }
    )
}



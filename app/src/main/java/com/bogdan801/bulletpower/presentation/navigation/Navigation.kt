package com.bogdan801.bulletpower.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bogdan801.bulletpower.presentation.screens.bullets.BulletsScreen
import com.bogdan801.bulletpower.presentation.screens.devices.DevicesScreen
import com.bogdan801.bulletpower.presentation.screens.graph.GraphScreen
import com.bogdan801.bulletpower.presentation.screens.home.HomeScreen
import com.bogdan801.bulletpower.presentation.screens.rating.RatingScreen
import com.bogdan801.bulletpower.presentation.screens.settings.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(Screen.Devices.route){
            DevicesScreen(navController = navController)
        }
        composable(Screen.Bullets.route){
            BulletsScreen(navController = navController)
        }
        composable(Screen.RatingSingleShot.route){
            RatingScreen(
                navController = navController,
                isSingleShot = true
            )
        }
        composable(Screen.RatingMultipleShot.route){
            RatingScreen(
                navController = navController,
                isSingleShot = false
            )
        }
        composable(Screen.Graph.route){
            GraphScreen(navController = navController)
        }
        composable(Screen.Settings.route){
            SettingsScreen(navController = navController)
        }
    }
}
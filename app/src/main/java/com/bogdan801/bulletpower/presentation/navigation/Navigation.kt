package com.bogdan801.bulletpower.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bogdan801.bulletpower.presentation.screens.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    //authUIClient: AuthUIClient
) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ){
        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }

    }
}
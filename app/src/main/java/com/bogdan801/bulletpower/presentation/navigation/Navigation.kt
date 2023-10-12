package com.bogdan801.bulletpower.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
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
    val speedOfTransition = 300

    val enterTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
        {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(speedOfTransition)
            )
        }
    val exitTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
        {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(speedOfTransition)
            )
        }
    val popEnterTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
        {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(speedOfTransition)
            )
        }
    val popExitTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
        {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(speedOfTransition)
            )
        }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Devices.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popExitTransition = popExitTransition
        ){
            DevicesScreen(navController = navController)
        }
        composable(
            route = Screen.Bullets.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popExitTransition = popExitTransition
        ){
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
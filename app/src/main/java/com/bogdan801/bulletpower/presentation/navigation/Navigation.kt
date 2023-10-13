package com.bogdan801.bulletpower.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bogdan801.bulletpower.domain.model.toShotList
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
    val speedOfTransition = 200

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route,
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true)
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Devices().route + "/{isScreenSelector}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isScreenSelector"){
                    type = NavType.IntType
                }
            )
        ){entry ->
            DevicesScreen(navController = navController, isScreenSelector = entry.arguments!!.getInt("isScreenSelector") == 1)
        }
        composable(
            route = Screen.Bullets().route + "/{isScreenSelector}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isScreenSelector"){
                    type = NavType.IntType
                }
            )
        ){ entry ->
            BulletsScreen(navController = navController, isScreenSelector = entry.arguments!!.getInt("isScreenSelector") == 1)
        }
        composable(
            route = Screen.Rating.route + "/{isSingleShot}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isSingleShot"){
                    type = NavType.IntType
                }
            )
        ){ entry ->
            RatingScreen(
                navController = navController,
                isSingleShot = entry.arguments!!.getInt("isSingleShot") == 1
            )
        }
        composable(
            route = Screen.Graph().route + "/{shots}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("shots"){
                    type = NavType.StringType
                }
            )

        ){ entry ->
            val shotsString = entry.arguments!!.getString("shots")!!
            GraphScreen(
                navController = navController,
                data = shotsString.toShotList()
            )
        }
        composable(
            route = Screen.Settings.route,
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true)
        ){
            SettingsScreen(navController = navController)
        }
    }
}
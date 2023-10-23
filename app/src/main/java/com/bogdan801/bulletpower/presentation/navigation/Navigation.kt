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
import com.bogdan801.bulletpower.presentation.screens.menu.MenuScreen

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
            HomeScreen(navController = navController, entry = it)
        }
        composable(
            route = Screen.Devices().route + "/{isScreenSelector}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isScreenSelector"){
                    type = NavType.IntType
                }
            )
        ){entry ->
            DevicesScreen(
                navController = navController,
                isScreenSelector = entry.arguments!!.getInt("isScreenSelector") == 1
            )
        }
        composable(
            route = Screen.Bullets().route + "/{isScreenSelector}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isScreenSelector"){
                    type = NavType.IntType
                }
            )
        ){ entry ->
            BulletsScreen(
                navController = navController,
                isScreenSelector = entry.arguments!!.getInt("isScreenSelector") == 1
            )
        }
        composable(
            route = Screen.Rating().route + "/{isSingleShot}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("isSingleShot"){
                    type = NavType.IntType
                }
            )
        ){
            RatingScreen(navController = navController)
        }
        composable(
            route = Screen.Graph().route + "/{shots}/{deviceName}/{bulletName}",
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true),
            arguments = listOf(
                navArgument("shots"){
                    type = NavType.StringType
                },
                navArgument("deviceName"){
                    type = NavType.StringType
                },
                navArgument("bulletName"){
                    type = NavType.StringType
                }
            )

        ){ entry ->
            val shotsString = entry.arguments!!.getString("shots")!!
            val deviceName = entry.arguments!!.getString("deviceName")!!
            val bulletName = entry.arguments!!.getString("bulletName")!!
            GraphScreen(
                navController = navController,
                data = shotsString.toShotList(),
                deviceName = if(deviceName == "-") null else deviceName,
                bulletName = if(bulletName == "-") null else bulletName
            )
        }
        composable(
            route = Screen.Menu.route,
            enterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition),
            exitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition),
            popEnterTransition = TransitionsUtil.enterSlideInTransition(speedOfTransition, true),
            popExitTransition = TransitionsUtil.exitSlideInTransition(speedOfTransition, true)
        ){
            MenuScreen(navController = navController)
        }
    }
}
package com.bogdan801.bulletpower.presentation.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Devices: Screen("devices")
    object Bullets: Screen("bullets")
    object Graph: Screen("graph")
    object RatingSingleShot: Screen("rating-single")
    object RatingMultipleShot: Screen("rating-multiple")
    object Settings: Screen("settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

package com.bogdan801.bulletpower.presentation.navigation

import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.toStringRepresentation

sealed class Screen(val route: String = "", val routeWithArgs: String = ""){
    object Home: Screen(route = "home", routeWithArgs = "home")
    data class Devices(val isSelectorScreen: Boolean = false) : Screen(route = "devices", routeWithArgs = "devices/" + (if(isSelectorScreen) "1" else "0"))
    data class Bullets(val isSelectorScreen: Boolean = false) : Screen(route = "bullets", routeWithArgs = "bullets/" + (if(isSelectorScreen) "1" else "0"))
    data class Graph(val shots: List<ShotRatingItem>? = null) : Screen(route = "graph", routeWithArgs = "graph/" + shots?.toStringRepresentation())
    object Rating: Screen(route = "rating", routeWithArgs = "rating")
    object Settings: Screen(route = "settings", routeWithArgs = "settings")
}

package com.bogdan801.bulletpower.presentation.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
}

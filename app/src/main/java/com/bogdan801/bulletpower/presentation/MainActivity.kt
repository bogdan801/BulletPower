package com.bogdan801.bulletpower.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bogdan801.bulletpower.presentation.navigation.Navigation
import com.bogdan801.bulletpower.presentation.theme.BulletPowerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BulletPowerTheme {
                Navigation(
                    navController = rememberNavController()
                )
            }
        }
    }
}

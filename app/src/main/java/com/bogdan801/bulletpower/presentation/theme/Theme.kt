package com.bogdan801.bulletpower.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val mainColorScheme = darkColorScheme(
    primary = Green50,
    onPrimary = Color.White,
    surface = Color.Black,
    background = Color.Black,
    onBackground = Gray80,
    onSurface = Gray80,
    onSurfaceVariant = Gray50,
    primaryContainer = Gray20,
    secondaryContainer = Gray10,
    outline = Gray40,
    outlineVariant = Gray30,
    error = Red10
)

@Composable
fun BulletPowerTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = mainColorScheme
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(color = colorScheme.secondaryContainer)
    systemUiController.setStatusBarColor(color = colorScheme.primaryContainer)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
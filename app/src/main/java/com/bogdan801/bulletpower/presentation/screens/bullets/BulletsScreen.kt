package com.bogdan801.bulletpower.presentation.screens.bullets

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.SearchBar
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation

@Composable
fun BulletsScreen(
    navController: NavController,
    viewModel: BulletsViewModel = hiltViewModel(),
    isScreenSelector: Boolean = false
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = {
                    focusManager.clearFocus()
                }
            ),
        topBar = {
            CustomTopAppBar(
                title = "Список куль",
                backButton = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
            ){
                SearchBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        )
                )
            }
        }
    }
}
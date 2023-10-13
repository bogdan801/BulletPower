package com.bogdan801.bulletpower.presentation.screens.graph

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation

@Composable
fun GraphScreen(
    navController: NavController,
    viewModel: GraphViewModel = hiltViewModel(),
    data: List<ShotRatingItem>
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ){

        }
    }
}
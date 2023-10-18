package com.bogdan801.bulletpower.presentation.screens.graph

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.Graph
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation

@Composable
fun GraphScreen(
    navController: NavController,
    viewModel: GraphViewModel = hiltViewModel(),
    data: List<ShotRatingItem>
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    LaunchedEffect(key1 = true){
        viewModel.convertDataToPoints(data)
    }

    Row (
        modifier = Modifier.fillMaxSize(),
    ){
        CustomTopAppBar(
            title = "Графік",
            isVertical = true,
            backButton = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ){
            Graph(
                modifier = Modifier.fillMaxSize(),
                data = when(screenState.dataToShow){
                    GraphDataType.Speed -> screenState.speedPoints
                    GraphDataType.Energy -> screenState.energyPoints
                },
                xTitle = "Постріл",
                yTitle = screenState.dataToShow.title
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(screenState.dataToShow == GraphDataType.Speed){
                        viewModel.setDataToShowType(GraphDataType.Energy)
                    }
                    else{
                        viewModel.setDataToShowType(GraphDataType.Speed)
                    }
                }
            ) {
                Text(
                    text = screenState.dataToShow.name,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Visible,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
    }
}
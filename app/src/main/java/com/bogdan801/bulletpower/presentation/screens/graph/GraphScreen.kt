package com.bogdan801.bulletpower.presentation.screens.graph

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.data.util.getCurrentTimeStamp
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.Graph
import com.bogdan801.bulletpower.presentation.components.Spacer
import com.bogdan801.bulletpower.presentation.components.ToggleButton
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun GraphScreen(
    navController: NavController,
    viewModel: GraphViewModel = hiltViewModel(),
    data: List<ShotRatingItem>,
    deviceName: String? = null,
    bulletName: String? = null
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
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

        val captureController = rememberCaptureController()
        var capture: ImageBitmap? by remember { mutableStateOf(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("image/png")
        ) { uri ->
            if (uri != null && capture != null) {
                context.contentResolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        capture!!.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 85, outputStream)
                        outputStream.flush()
                        Toast.makeText(context, "Графік збережено", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Capturable(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            controller = captureController,
            onCaptured = { bitmap, error ->
                if (bitmap != null) {
                    capture = bitmap
                    val type = if(screenState.dataToShow == GraphDataType.Energy) "Графік_Енергії"
                               else "Графік_Швидкості"
                    val device = if(deviceName != null) "_$deviceName" else ""
                    val bullet = if(bulletName != null) "_$bulletName" else ""
                    val timeStamp = "_" + getCurrentTimeStamp()
                    val defaultTitle = "$type$device$bullet$timeStamp.png"
                    launcher.launch(defaultTitle)
                }

                if (error != null) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ){
                AnimatedContent(
                    targetState = screenState.dataToShow == GraphDataType.Energy,
                    transitionSpec = {
                        fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                    },
                    label = ""
                ) {
                    if(it){
                        Graph(
                            modifier = Modifier.fillMaxSize(),
                            data = screenState.energyPoints,
                            xTitle = "Постріл",
                            yTitle = screenState.dataToShow.title
                        )
                    }
                    else {
                        Graph(
                            modifier = Modifier.fillMaxSize(),
                            data = screenState.speedPoints,
                            xTitle = "Постріл",
                            yTitle = screenState.dataToShow.title
                        )
                    }
                }
                if(deviceName != null || bulletName != null){
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = 6.dp),
                        text = if(deviceName == null) "$bulletName"
                               else if(bulletName == null) "$deviceName"
                               else "$deviceName - $bulletName",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ){
            ToggleButton(
                modifier = Modifier.size(64.dp),
                icon = Icons.Outlined.Bolt,
                title = "Енергія",
                isToggled = screenState.dataToShow == GraphDataType.Energy,
                onClick = {
                    viewModel.setDataToShowType(GraphDataType.Energy)
                }
            )
            Spacer(h = 4.dp)
            ToggleButton(
                modifier = Modifier.size(64.dp),
                icon = Icons.Outlined.Speed,
                title = "Швидкість",
                isToggled = screenState.dataToShow == GraphDataType.Speed,
                onClick = {
                    viewModel.setDataToShowType(GraphDataType.Speed)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            ToggleButton(
                modifier = Modifier.size(64.dp),
                icon = Icons.Outlined.AddAPhoto,
                title = "Зберегти",
                isToggleable = false,
                onClick = {
                    captureController.capture(Bitmap.Config.ARGB_8888)
                }
            )
        }
    }
}
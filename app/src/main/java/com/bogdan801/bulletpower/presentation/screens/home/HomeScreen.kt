package com.bogdan801.bulletpower.presentation.screens.home

import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.R
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.presentation.components.ButtonGridCell
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.DigitDisplay
import com.bogdan801.bulletpower.presentation.components.DisplayGridCell
import com.bogdan801.bulletpower.presentation.components.DisplaySize
import com.bogdan801.bulletpower.presentation.components.EmptyGridCell
import com.bogdan801.bulletpower.presentation.components.SelectionItem
import com.bogdan801.bulletpower.presentation.components.SelectorButton
import com.bogdan801.bulletpower.presentation.components.ShotRow
import com.bogdan801.bulletpower.presentation.components.Spacer
import com.bogdan801.bulletpower.presentation.components.StatsRow
import com.bogdan801.bulletpower.presentation.components.StatsRowType
import com.bogdan801.bulletpower.presentation.components.TextGridCell
import com.bogdan801.bulletpower.presentation.navigation.Screen
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = "Енергія кулі",
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Settings.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Налаштування"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectorButton(
                selectionItem = SelectionItem.Device,
                selectedDevice = screenState.device,
                onClick = {
                    navController.navigate(Screen.Devices.route)
                }
            )
            Spacer(h = 1.dp)
            SelectorButton(
                selectionItem = SelectionItem.Bullet,
                selectedBullet = screenState.bullet,
                onClick = {
                    navController.navigate(Screen.Bullets.route)
                }
            )

            Spacer(h = 24.dp)
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Маса кулі",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    DigitDisplay(
                        value = screenState.bulletWeight,
                        onValueChange = { newValue ->
                            viewModel.setBulletWeight(newValue)
                        },
                        digitCount = 4,
                        dotAfterDigit = 1,
                        displaySize = DisplaySize.Large,
                        isReadOnly = screenState.bullet != null
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "гр.",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(h = 24.dp)

            var tabIndexState by rememberSaveable { mutableIntStateOf(0) }
            TabRow(
                selectedTabIndex = tabIndexState,
                indicator = @Composable { tabPositions ->
                    if (tabIndexState < tabPositions.size) {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndexState]),
                            width = tabPositions[tabIndexState].contentWidth,
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Tab(
                    selected = tabIndexState == 0,
                    text = {
                        Text(text = "Одиночний")
                    },
                    onClick = { tabIndexState = 0 },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                    interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()
                        override suspend fun emit(interaction: Interaction) {}
                        override fun tryEmit(interaction: Interaction) = true
                    }
                )
                Tab(
                    selected = tabIndexState == 1,
                    text = {
                        Text(text = "Серія")
                    },
                    onClick = { tabIndexState = 1 },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                    interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()
                        override suspend fun emit(interaction: Interaction) {}
                        override fun tryEmit(interaction: Interaction) = true
                    }
                )
            }

            val pagerState = rememberPagerState { 2 }
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {page ->
                when(page){
                    0 -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp)
                            ) {
                                TextGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    text = "Швидкість, м/с"
                                )
                                Spacer(w = 1.dp)
                                TextGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    text = "Енергія, Дж"
                                )
                            }
                            Spacer(h = 1.dp)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(95.dp)
                            ) {
                                DisplayGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    value = screenState.singleShotSpeed,
                                    onValueChange = { newValue ->
                                        viewModel.setSingleShotSpeed(newValue)
                                    },
                                    dotAfterDigit = 3
                                )
                                Spacer(w = 1.dp)
                                DisplayGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    value = screenState.singleShotEnergy,
                                    isReadOnly = true,
                                    dotAfterDigit = null
                                )
                            }
                            Spacer(h = 1.dp)
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                            if(screenState.device != null && screenState.bullet != null) {
                                ButtonGridCell(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    title = "Додати до рейтингу",
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_rating),
                                            contentDescription = ""
                                        )
                                    },
                                    onClick = {}
                                )
                            }
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                            ) {
                                ShotRow(isTitle = true)
                                Spacer(h = 1.dp)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(58.dp)
                                ) {
                                    TextGridCell(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(36.dp),
                                        text = (screenState.shotSeries.size + 1).toString()
                                    )
                                    Spacer(w = 1.dp)
                                    DisplayGridCell(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f),
                                        displaySize = DisplaySize.Small,
                                        dotAfterDigit = 3,
                                        value = screenState.multipleShotSpeed,
                                        shouldCloseKeyboard = false,
                                        onValueChange = { newValue ->
                                            viewModel.setMultipleShotSpeed(newValue)
                                        }
                                    )
                                    Spacer(w = 1.dp)
                                    DisplayGridCell(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f),
                                        displaySize = DisplaySize.Small,
                                        dotAfterDigit = null,
                                        value = screenState.multipleShotEnergy,
                                        isReadOnly = true
                                    )
                                }
                                Spacer(h = 1.dp)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(36.dp)
                                ) {
                                    TextGridCell(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(36.dp),
                                        text = "+",
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                    Spacer(w = 1.dp)
                                    ButtonGridCell(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f),
                                        title = "Додати постріл до серії",
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        onClick = {
                                            if(screenState.multipleShotSpeed == 0.0){
                                                Toast.makeText(
                                                    context,
                                                    "Швидкість пострілу не може дорівнювати 0!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            else if(screenState.bulletWeight == 0.0){
                                                Toast.makeText(
                                                    context,
                                                    "Маса кулі не може дорівнювати 0!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            else{
                                                viewModel.addShotToTheSeries(
                                                    ShotRatingItem(
                                                        speed = screenState.multipleShotSpeed,
                                                        energy = screenState.multipleShotEnergy
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                                Spacer(h = 1.dp)
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    items(screenState.shotSeries.size){ i ->
                                        val shotID = screenState.shotSeries.lastIndex - i
                                        ShotRow(
                                            modifier = Modifier.combinedClickable(
                                                indication = null,
                                                interactionSource = remember {
                                                    MutableInteractionSource()
                                                },
                                                onLongClick = {
                                                    viewModel.removeShotFromTheSeries(shotID)
                                                },
                                                onClick = {}
                                            ),
                                            number = screenState.shotSeries.size - i,
                                            shot = screenState.shotSeries[shotID]
                                        )
                                        Spacer(h = 1.dp)
                                    }
                                }
                            }
                            if(screenState.shotSeries.size > 1){
                                Spacer(h = 1.dp)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        StatsRow()
                                        Spacer(h = 1.dp)

                                        val minSpeed by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.minOf { it.speed }
                                                }
                                                else 0.0
                                            }
                                        }
                                        val medSpeed by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.sumOf { it.speed } /
                                                    screenState.shotSeries.size
                                                }
                                                else 0.0
                                            }
                                        }
                                        val maxSpeed by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.maxOf { it.speed }
                                                }
                                                else 0.0
                                            }
                                        }
                                        StatsRow(
                                            type = StatsRowType.Speed(minSpeed, medSpeed, maxSpeed)
                                        )
                                        Spacer(h = 1.dp)

                                        val minEnergy by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.minOf { it.energy }
                                                }
                                                else 0.0
                                            }
                                        }
                                        val medEnergy by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.sumOf { it.energy } /
                                                    screenState.shotSeries.size
                                                }
                                                else 0.0
                                            }
                                        }
                                        val maxEnergy by remember {
                                            derivedStateOf {
                                                if(screenState.shotSeries.isNotEmpty()) {
                                                    screenState.shotSeries.maxOf { it.energy }
                                                }
                                                else 0.0
                                            }
                                        }
                                        StatsRow(
                                            type = StatsRowType.Energy(minEnergy, medEnergy, maxEnergy)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                        ) {
                                            ButtonGridCell(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .weight(1f),
                                                title = "Графік",
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_graph),
                                                        contentDescription = ""
                                                    )
                                                },
                                                onClick = {

                                                }
                                            )
                                            Spacer(w = 1.dp)
                                            ButtonGridCell(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .weight(1f),
                                                title = "Рейтинг",
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_rating),
                                                        contentDescription = ""
                                                    )
                                                },
                                                onClick = {

                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            LaunchedEffect(tabIndexState){
                pagerState.animateScrollToPage(tabIndexState)
            }
            LaunchedEffect(pagerState.currentPage){
                tabIndexState = pagerState.targetPage
            }
        }
    }
}
package com.bogdan801.bulletpower.presentation.screens.home

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.R
import com.bogdan801.bulletpower.presentation.components.ButtonGridCell
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.DigitDisplay
import com.bogdan801.bulletpower.presentation.components.DisplayGridCell
import com.bogdan801.bulletpower.presentation.components.DisplaySize
import com.bogdan801.bulletpower.presentation.components.EmptyGridCell
import com.bogdan801.bulletpower.presentation.components.SelectionItem
import com.bogdan801.bulletpower.presentation.components.SelectorButton
import com.bogdan801.bulletpower.presentation.components.TextGridCell
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
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = "Енергія кулі",
                actions = {
                    IconButton(onClick = {}) {
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
                onClick = {}
            )
            Spacer(modifier = Modifier.height(1.dp))
            SelectorButton(
                selectionItem = SelectionItem.Bullet,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))
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
                    var displayValue by rememberSaveable { mutableDoubleStateOf(0.0) }
                    DigitDisplay(
                        value = displayValue,
                        onValueChange = { newValue ->
                            displayValue = newValue
                        },
                        digitCount = 4,
                        dotAfterDigit = 1,
                        displaySize = DisplaySize.Large
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "гр.",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

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
                                Spacer(modifier = Modifier.width(1.dp))
                                TextGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    text = "Енергія, Дж"
                                )
                            }
                            Spacer(modifier = Modifier.height(1.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(98.dp)
                            ) {
                                var speedDisplay by rememberSaveable { mutableDoubleStateOf(0.0) }
                                DisplayGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    value = speedDisplay,
                                    onValueChange = { newValue ->
                                        speedDisplay = newValue
                                    }
                                )
                                Spacer(modifier = Modifier.width(1.dp))
                                var energyDisplay by rememberSaveable { mutableDoubleStateOf(0.0) }
                                DisplayGridCell(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    value = energyDisplay,
                                    onValueChange = { newValue ->
                                        energyDisplay = newValue
                                    },
                                    isReadOnly = true
                                )
                            }
                            Spacer(modifier = Modifier.height(1.dp))
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                            ButtonGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                title = "Додати до рейтингу",
                                icon = {
                                    Icon(painter = painterResource(id = R.drawable.ic_rating), contentDescription = "")
                                },
                                onClick = {}
                            )
                        }
                    }
                    1 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Page 1")
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
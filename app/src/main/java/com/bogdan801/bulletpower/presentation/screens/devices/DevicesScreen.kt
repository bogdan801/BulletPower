package com.bogdan801.bulletpower.presentation.screens.devices

import android.content.pm.ActivityInfo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.presentation.components.ConfirmationDialog
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.DeviceItem
import com.bogdan801.bulletpower.presentation.components.EmptyGridCell
import com.bogdan801.bulletpower.presentation.components.SearchBar
import com.bogdan801.bulletpower.presentation.components.SelectionItem
import com.bogdan801.bulletpower.presentation.components.Spacer
import com.bogdan801.bulletpower.presentation.components.TextGridCell
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: DevicesViewModel = hiltViewModel(),
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
                title = "Список пристроїв",
                backButton = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Налаштування"
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Додати"
                )
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ){
            if(screenState.items.isNotEmpty() || screenState.foundItems.isNotEmpty()){
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
                            ),
                        searchQuery = screenState.searchQuery,
                        onSearch = {
                            viewModel.doSearch(it)
                        }
                    )
                }
            }



            if(screenState.searchQuery.isBlank()){
                if(screenState.items.isNotEmpty()){
                    Spacer(h = 1.dp)
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ){
                        if(isScreenSelector){
                            item {
                                SelectionItem(
                                    isItemProvided = false,
                                    emptyItemTitle = "Без пристрою"
                                )
                            }
                        }
                        items(screenState.items){ device ->
                            DeviceItem(
                                modifier = Modifier.animateItemPlacement(),
                                device = device,
                                onClick = {

                                },
                                onEditClick = {

                                },
                                onDeleteClick = {

                                }
                            )
                        }
                        item {
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp)
                            )
                        }
                    }
                    EmptyGridCell(modifier = Modifier.fillMaxSize())
                }
                else {
                    TextGridCell(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        text = "Список порожній.\nДодайте пристрій натиснувши на \"+\""
                    )
                }
            }
            else {
                if(screenState.foundItems.isNotEmpty()){
                    Spacer(h = 1.dp)
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ){
                        items(screenState.foundItems){ device ->
                            DeviceItem(
                                device = device,
                                onClick = {

                                },
                                onEditClick = {

                                },
                                onDeleteClick = {

                                }
                            )
                        }
                        item {
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp)
                            )
                        }
                    }
                    EmptyGridCell(modifier = Modifier.fillMaxSize())
                }
                else {
                    TextGridCell(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        text = "Пристрій не знайдено"
                    )
                }
            }
        }
    }
}
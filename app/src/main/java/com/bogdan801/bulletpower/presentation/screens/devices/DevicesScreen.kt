package com.bogdan801.bulletpower.presentation.screens.devices

import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.presentation.components.AddEditDeviceDialogBox
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
    val context = LocalContext.current
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
                    val backAction = {
                        val selectedDevice: Device? = navController
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.get("device")

                        if(selectedDevice != null){
                            val found = screenState.items.find { it.deviceID == selectedDevice.deviceID }
                            if(found != null){
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set<Device?>("device", found)
                                viewModel.setCaliberLimit(found.caliber)
                            }
                            else {
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.remove<Device?>("device")
                            }
                        }
                    }

                    BackHandler(true){
                        if(isScreenSelector) backAction()
                        navController.popBackStack()
                    }

                    IconButton(
                        onClick = {
                            if(isScreenSelector) backAction()
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
            var showAddDialog by rememberSaveable {
                mutableStateOf(false)
            }
            FloatingActionButton(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    showAddDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Додати"
                )
            }
            AddEditDeviceDialogBox(
                showDialog = showAddDialog,
                onDismiss = {
                    showAddDialog = false
                },
                onSave = { device ->
                    viewModel.addDevice(device)
                    showAddDialog = false
                }
            )
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
                                    modifier = Modifier.animateItemPlacement(),
                                    isItemProvided = false,
                                    emptyItemTitle = "Без пристрою",
                                    onItemClick = {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.remove<Device?>("device")

                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        items(
                            items = screenState.items,
                            key = { it.deviceID }
                        ){ device ->
                            DeviceItem(
                                modifier = Modifier.animateItemPlacement(tween(200)),
                                device = device,
                                onClick = {
                                    if (isScreenSelector){
                                        if(viewModel.lockedCaliber != null && device.caliber != viewModel.lockedCaliber){
                                            Toast.makeText(
                                                context,
                                                "Пристрій з даним колібром не відповідає обраній кулі",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        else{
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set<Device?>("device", device)

                                            navController.popBackStack()
                                        }
                                    }
                                },
                                onEditClick = { editedDevice ->
                                    viewModel.editDevice(editedDevice)
                                },
                                onDeleteClick = { id ->
                                    viewModel.deleteDevice(id)
                                    /*if(selectedDevice != null){
                                        if(selectedDevice!!.deviceID == id){
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.remove<Device?>("device")
                                        }
                                    }*/
                                }
                            )
                        }
                        item {
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp)
                                    .animateItemPlacement()
                            )
                        }
                    }
                    EmptyGridCell(modifier = Modifier.fillMaxSize())
                }
                else {
                    TextGridCell(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
                        items(
                            items = screenState.items,
                            key = { it.deviceID }
                        ){ device ->
                            DeviceItem(
                                modifier = Modifier.animateItemPlacement(tween(200)),
                                device = device,
                                onClick = {
                                    if(isScreenSelector){
                                        if(viewModel.lockedCaliber != null && device.caliber != viewModel.lockedCaliber){
                                            Toast.makeText(
                                                context,
                                                "Пристрій з даним колібром не відповідає обраній кулі",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        else {
                                            viewModel.setCaliberLimit(device.caliber)
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set<Device?>("device", device)

                                            navController.popBackStack()
                                        }
                                    }
                                },
                                onEditClick = { editedDevice ->
                                    viewModel.editDevice(editedDevice)
                                    viewModel.clearFound()
                                    viewModel.doSearch(screenState.searchQuery, 200)
                                },
                                onDeleteClick = { id ->
                                    viewModel.deleteDevice(id)
                                    viewModel.clearFound()
                                    viewModel.doSearch(screenState.searchQuery, 200)
                                    /*if(selectedDevice != null){
                                        if(selectedDevice!!.deviceID == id){
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.remove<Device?>("device")
                                        }
                                    }*/
                                }
                            )
                        }
                        item {
                            EmptyGridCell(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp)
                                    .animateItemPlacement()
                            )
                        }
                    }
                    EmptyGridCell(modifier = Modifier.fillMaxSize())
                }
                else {
                    TextGridCell(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        text = "Пристрій не знайдено"
                    )
                }
            }
        }
    }
}
package com.bogdan801.bulletpower.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.DigitDisplay
import com.bogdan801.bulletpower.presentation.components.SelectionItem
import com.bogdan801.bulletpower.presentation.components.SelectorButton

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

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
                onClick = {

                }
            )
            Spacer(modifier = Modifier.height(1.dp))
            SelectorButton(
                selectionItem = SelectionItem.Bullet,
                onClick = {

                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            var displayValue by rememberSaveable { mutableStateOf(0.0) }
            DigitDisplay(
                value = displayValue,
                onValueChange = { newValue ->
                    displayValue = newValue
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = displayValue.toString())
        }
    }
}
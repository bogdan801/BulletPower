package com.bogdan801.bulletpower.presentation.screens.rating

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.MultipleShotRatingCard
import com.bogdan801.bulletpower.presentation.components.SearchBar
import com.bogdan801.bulletpower.presentation.components.SingleShotRatingCard
import com.bogdan801.bulletpower.presentation.components.SortBy
import com.bogdan801.bulletpower.presentation.components.SortBySelector
import com.bogdan801.bulletpower.presentation.components.Spacer
import com.bogdan801.bulletpower.presentation.components.TitleRatingCard
import com.bogdan801.bulletpower.presentation.navigation.Screen
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingScreen(
    navController: NavController,
    viewModel: RatingViewModel = hiltViewModel(),
    //isSingleShot: Boolean = true
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = {
                    focusManager.clearFocus()
                }
            ),
    ) {
        CustomTopAppBar(
            title = if(screenState.isSingleShot) "Одиночний рейтинг" else "Рейтинг серій",
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    searchQuery = screenState.searchQuery,
                    onSearch = { newQuery ->
                        viewModel.doSearch(newQuery)
                    }
                )
                Spacer(w = 16.dp)
                SortBySelector(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    selected = screenState.sortBy.ordinal,
                    sortByItems = SortBy.values().map { it.title },
                    sortOrder = screenState.sortOrder,
                    onSortOrderChange = { newOrder ->
                        viewModel.setSortOrder(newOrder)

                    },
                    onSortByItemSelected = { i ->
                        viewModel.setSortBy(SortBy.values()[i])
                    }
                )
            }
            Spacer(h = 1.dp)
            if(screenState.isSingleShot){
                if(screenState.searchQuery.isBlank()){
                    if(screenState.singleShotList.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ){
                            item {
                                TitleRatingCard(
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            itemsIndexed(
                                items = screenState.singleShotList,
                                key = { _, it ->
                                    it.singleShotID
                                }
                            ){ index, item ->
                                SingleShotRatingCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                    number = index + 1,
                                    item = item,
                                    onDeleteItemClick = {
                                        viewModel.deleteSingleShotRating(it)
                                    },
                                    onEditItem = {
                                        viewModel.editSingleShotRating(it)
                                    }
                                )
                            }
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Рейтинг порожній",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                else {
                    if(screenState.foundSingleShots.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ){
                            item {
                                TitleRatingCard(
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            itemsIndexed(
                                items = screenState.foundSingleShots,
                                key = { _, it ->
                                    it.singleShotID
                                }
                            ){ index, item ->
                                SingleShotRatingCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                    number = index + 1,
                                    item = item,
                                    onDeleteItemClick = {
                                        viewModel.deleteSingleShotRating(it, doSearch = true)
                                    },
                                    onEditItem = {
                                        viewModel.editSingleShotRating(it, doSearch = true)
                                    }
                                )
                            }
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Записи не знайдено",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            else {
                if(screenState.searchQuery.isBlank()){
                    if(screenState.multipleShotList.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ){
                            item {
                                TitleRatingCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    isSingleShot = false
                                )
                            }
                            itemsIndexed(
                                items = screenState.multipleShotList,
                                key = { _, it ->
                                    it.multipleShotID
                                }
                            ){ index, item ->
                                MultipleShotRatingCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                    item = item,
                                    number = index + 1,
                                    isExpanded = screenState.expandedMutableShotCards[item.multipleShotID] ?: false,
                                    onExpandToggle = {
                                        viewModel.toggleMultipleShotCard(item.multipleShotID)
                                    },
                                    onDeleteItemClick = {
                                        viewModel.deleteMultipleShotRating(it)
                                    },
                                    onDrawGraphClick = {
                                        navController.navigate(
                                            Screen.Graph(
                                                shots = item.shots,
                                                deviceName = item.device?.name,
                                                bulletName = item.bullet?.name
                                            ).routeWithArgs
                                        )
                                    },
                                    onDeleteSubItemClick = {
                                        viewModel.deleteShotFromMultipleShotRating(it)
                                    },
                                    onEditSubItemClick = {
                                        viewModel.editShotFromMultipleShotRating(it)
                                    }
                                )
                            }
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Рейтинг порожній",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                else {
                    if(screenState.foundMultipleShots.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ){
                            item {
                                TitleRatingCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    isSingleShot = false
                                )
                            }
                            itemsIndexed(
                                items = screenState.foundMultipleShots,
                                key = { _, it ->
                                    it.multipleShotID
                                }
                            ){ index, item ->
                                MultipleShotRatingCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                    item = item,
                                    number = index + 1,
                                    isExpanded = screenState.expandedMutableShotCards[item.multipleShotID] ?: false,
                                    onExpandToggle = {
                                        viewModel.toggleMultipleShotCard(item.multipleShotID)
                                    },
                                    onDeleteItemClick = {
                                        viewModel.deleteMultipleShotRating(it, doSearch = true)
                                    },
                                    onDrawGraphClick = {
                                        navController.navigate(
                                            Screen.Graph(
                                                shots = item.shots,
                                                deviceName = item.device?.name,
                                                bulletName = item.bullet?.name
                                            ).routeWithArgs
                                        )
                                    },
                                    onDeleteSubItemClick = {
                                        viewModel.deleteShotFromMultipleShotRating(it, doSearch = true)
                                    },
                                    onEditSubItemClick = {
                                        viewModel.editShotFromMultipleShotRating(it, doSearch = true)
                                    }
                                )
                            }
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Записи не знайдено",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
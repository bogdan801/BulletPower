package com.bogdan801.bulletpower.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SortBySelector(
    modifier: Modifier = Modifier,
    selected: Int = 0,
    sortByItems: List<String>,
    sortOrder: SortOrder = SortOrder.Ascending,
    onSortByItemSelected: (Int) -> Unit = {},
    onSortOrderChange: (SortOrder) -> Unit = {}
) {
    var showItems by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.clickable(
            interactionSource = remember{ MutableInteractionSource() },
            indication = null,
            onClick = { showItems = true }
        ),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if(showItems) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        ),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(w = 16.dp)
            Text(
                text = "Сортувати за: ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                modifier = Modifier.weight(1f),
                text = sortByItems[selected],
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start
            )
            IconButton(
                onClick = {
                    onSortOrderChange(
                        if(sortOrder == SortOrder.Ascending) SortOrder.Descending
                        else SortOrder.Ascending
                    )
                }
            ) {
                val angle by animateFloatAsState(
                    targetValue = if(sortOrder == SortOrder.Ascending) 0f else -180f,
                    label = ""
                )
                Icon(
                    modifier = Modifier.rotate(angle),
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = ""
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            expanded = showItems,
            onDismissRequest = { showItems = false }
        ) {
            sortByItems.forEachIndexed { i, item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item)
                    },
                    onClick = {
                        showItems = false
                        onSortByItemSelected(i)
                    }
                )
            }
        }
    }
}

enum class SortBy(val title: String) {
    DeviceName("Назва пристрою"),
    DeviceType("Тип пристрою"),
    DeviceCaliber("Калібр пристрою"),
    BulletName("Назва кулі"),
    BulletWeight("Вага кулі"),
    BulletCaliber("Калібр кулі"),
    Speed("Швидкість"),
    Energy("Енергія")
}

enum class SortOrder {
    Ascending, Descending
}
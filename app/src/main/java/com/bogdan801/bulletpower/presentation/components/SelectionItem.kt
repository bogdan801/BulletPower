package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device

@Composable
fun DeviceItem(
    modifier: Modifier = Modifier,
    device: Device,
    onClick: () -> Unit = {},
    onEditClick: (Device) -> Unit = {},
    onDeleteClick: (deviceID: Int) -> Unit = {}
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    ConfirmationDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            onDeleteClick(device.deviceID)
        },
        title = "Видалити пристрій?",
        subtitle = "Ви дійсно бажаєте видалити ${device.name} зі списку пристроїв?"
    )

    var showEditDialog by rememberSaveable {
        mutableStateOf(false)
    }
    AddEditDeviceDialogBox(
        showDialog = showEditDialog,
        onDismiss = {
            showEditDialog = false
        },
        defaultValues = device,
        title = "Редагування пристрою",
        onSave = {
            showEditDialog = false
            onEditClick(it)
        }
    )

    SelectionItem(
        modifier = modifier,
        itemTitle = device.name,
        itemSubtitle = "${device.type} • ${device.caliber}мм",
        onItemClick = onClick,
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true }
    )
}

@Composable
fun BulletItem(
    modifier: Modifier = Modifier,
    bullet: Bullet,
    onClick: () -> Unit = {},
    onEditClick: (bullet: Bullet) -> Unit = {},
    onDeleteClick: (bulletID: Int) -> Unit = {}
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    ConfirmationDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            onDeleteClick(bullet.bulletID)
        },
        title = "Видалити кулю?",
        subtitle = "Ви дійсно бажаєте видалити ${bullet.name} зі списку куль?"
    )

    var showEditDialog by rememberSaveable {
        mutableStateOf(false)
    }
    AddEditBulletDialogBox(
        showDialog = showEditDialog,
        onDismiss = {
            showEditDialog = false
        },
        defaultValues = bullet,
        title = "Редагування пристрою",
        onSave = {
            showEditDialog = false
            onEditClick(it)
        }
    )
    SelectionItem(
        modifier = modifier,
        itemTitle = bullet.name,
        itemSubtitle = "${bullet.weight}гр. • ${bullet.caliber}мм",
        onItemClick = onClick,
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true }
    )
}

@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    isItemProvided: Boolean = true,
    itemTitle: String = "",
    itemSubtitle: String = "",
    onItemClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    emptyItemTitle: String = ""
) {
    if(isItemProvided){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable(
                        onClick = onItemClick
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = itemTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(h = 4.dp)
                Text(
                    text = itemSubtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(54.dp)
                    .clickable(
                        onClick = onEditClick
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(54.dp)
                    .clickable(
                        onClick = onDeleteClick
                    )
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
    else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable(onClick = onItemClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Block,
                contentDescription = null
            )
            Spacer(w = 8.dp)
            Text(
                text = emptyItemTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
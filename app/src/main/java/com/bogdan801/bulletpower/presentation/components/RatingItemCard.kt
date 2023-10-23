package com.bogdan801.bulletpower.presentation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.data.util.calculateBulletEnergyUtil
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem

@Composable
fun SingleShotRatingCard(
    modifier: Modifier = Modifier,
    number: Int = 0,
    item: SingleShotRatingItem,
    weights: List<Float> = listOf(2f, 2f, 1f, 1f),
    height: Dp = 64.dp,
    onDeleteItemClick: (id: Int) -> Unit,
    onEditItem: (SingleShotRatingItem) -> Unit
) {
    val context = LocalContext.current
    var showDropDownMenu by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var longClickOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = modifier
            .height(height)
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = { offset ->
                        showDropDownMenu = true
                        longClickOffset = DpOffset(x = offset.x.toDp(), y = offset.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
    ) {
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .width(36.dp),
            text = number.toString()
        )
        Spacer(w = 1.dp)
        ItemGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[0]),
            itemTitle = item.device?.name ?: "",
            itemSubtitle1 = item.device?.type ?: "",
            itemSubtitle2 = item.device?.caliber.toString() + "мм"
        )
        Spacer(w = 1.dp)
        ItemGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[1]),
            itemTitle = item.bullet?.name ?: "",
            itemSubtitle1 = item.bullet?.weight.toString() + "гр.",
            itemSubtitle2 = item.bullet?.caliber.toString() + "мм"
        )
        Spacer(w = 1.dp)
        var editedValue by remember { mutableStateOf(item.speed.toString()) }
        LaunchedEffect(key1 = isEditing){
            if(isEditing) editedValue = item.speed.toString()
        }
        if(isEditing){
            NumberEditGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[2]),
                value = editedValue,
                onNumberChange = { newString ->
                    editedValue = newString
                },
                onDone = {
                    if(editedValue.isBlank()) {
                        Toast.makeText(
                            context,
                            "Поле швидкості не може бути порожнім!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    if(editedValue.toDouble() == 0.0){
                        Toast.makeText(
                            context,
                            "Швидкість не може дорівнювати 0!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    if(editedValue.toDouble() > 999.9){
                        Toast.makeText(
                            context,
                            "Швидкість повинна бути менше 1000м/с!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    val newSpeed = editedValue.toDouble()
                    val newEnergy = calculateBulletEnergyUtil(newSpeed, item.bullet!!.weight)
                    isEditing = false
                    onEditItem(
                        item.copy(
                            speed = newSpeed,
                            energy = newEnergy
                        )
                    )
                }
            )
        }
        else {
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[2]),
                text = String.format("%.1f", item.speed),
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[3]),
            text = String.format("%.3f", item.energy),
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
        DropdownMenu(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            expanded = showDropDownMenu,
            onDismissRequest = { showDropDownMenu = false },
            offset = longClickOffset.copy(y = longClickOffset.y - height)
        ) {
            DropdownMenuItem(
                text = {
                    Text("Видалити запис")
                },
                onClick = {
                    showDropDownMenu = false
                    onDeleteItemClick(item.singleShotID)
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Редагувати")
                },
                onClick = {
                    showDropDownMenu = false
                    isEditing = true
                }
            )
        }
    }
}

@Composable
fun MultipleShotRatingCard(
    modifier: Modifier = Modifier,
    number: Int = 0,
    item: MultipleShotRatingItem,
    weights: List<Float> = listOf(2f, 2f, 1f, 1f),
    height: Dp = 64.dp,
    isExpanded: Boolean = false,
    onExpandToggle: () -> Unit = {},
    onDeleteItemClick: (id: Int) -> Unit,
    onDrawGraphClick: () -> Unit,
    onDeleteSubItemClick: (id: Int) -> Unit,
    onEditSubItemClick: (ShotRatingItem) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        var showDropDownMenu by remember { mutableStateOf(false) }
        var longClickOffset by remember {
            mutableStateOf(DpOffset.Zero)
        }
        val interactionSource = remember {
            MutableInteractionSource()
        }
        Row(
            modifier = Modifier
                .height(height)
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = { offset ->
                            showDropDownMenu = true
                            longClickOffset = DpOffset(x = offset.x.toDp(), y = offset.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
        ) {
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(36.dp),
                text = number.toString()
            )
            Spacer(w = 1.dp)
            ItemGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[0]),
                itemTitle = item.device?.name ?: "",
                itemSubtitle1 = item.device?.type ?: "",
                itemSubtitle2 = item.device?.caliber.toString() + "мм"
            )
            Spacer(w = 1.dp)
            ItemGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[1]),
                itemTitle = item.bullet?.name ?: "",
                itemSubtitle1 = item.bullet?.weight.toString() + "гр.",
                itemSubtitle2 = item.bullet?.caliber.toString() + "мм"
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[2]),
                text = String.format("%.1f", item.averageSpeed),
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[3]),
                text = String.format("%.3f", item.averageEnergy),
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(w = 1.dp)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(height)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = onExpandToggle
                    ),
                contentAlignment = Alignment.Center
            ){
                val angle by animateFloatAsState(targetValue = if(!isExpanded) 0f else -180f, label = "")
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .rotate(angle),
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            DropdownMenu(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                expanded = showDropDownMenu,
                onDismissRequest = { showDropDownMenu = false },
                offset = longClickOffset.copy(y = longClickOffset.y - height)
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Видалити запис")
                    },
                    onClick = {
                        showDropDownMenu = false
                        onDeleteItemClick(item.multipleShotID)
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text("Графік")
                    },
                    onClick = {
                        showDropDownMenu = false
                        onDrawGraphClick()
                    }
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.fillMaxWidth()){
                Spacer(h = 1.dp)
                item.shots.forEachIndexed { index, shotItem ->
                    RatingShotRow(
                        modifier = Modifier.fillMaxWidth(),
                        number = item.shots.size - index,
                        item = shotItem,
                        bulletWeight = item.bullet?.weight ?: 0.0,
                        weights = weights,
                        onDeleteItem = {
                            onDeleteSubItemClick(it)
                        },
                        onEditItem = {
                            onEditSubItemClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RatingShotRow(
    modifier: Modifier = Modifier,
    number: Int = 0,
    item: ShotRatingItem,
    bulletWeight: Double = 0.0,
    weights: List<Float> = listOf(2f, 2f, 1f, 1f),
    height: Dp = 24.dp,
    onDeleteItem: (id: Int) -> Unit = {},
    onEditItem: (editedItem: ShotRatingItem) -> Unit = {}
) {
    val context = LocalContext.current
    var showDropDownMenu by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var longClickOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = modifier
            .height(height)
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = { offset ->
                        showDropDownMenu = true
                        longClickOffset = DpOffset(x = offset.x.toDp(), y = offset.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
    ) {
        EmptyGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .width(36.dp)
        )
        Spacer(w = 1.dp, background = MaterialTheme.colorScheme.secondaryContainer)
        EmptyGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[0])
        )
        Spacer(w = 1.dp, background = MaterialTheme.colorScheme.secondaryContainer)
        EmptyGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[1])
        )
        Spacer(w = 1.dp)
        var editedValue by remember { mutableStateOf(item.speed.toString()) }
        LaunchedEffect(key1 = isEditing){
            if(isEditing) editedValue = item.speed.toString()
        }
        if(isEditing){
            NumberEditGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[2]),
                value = editedValue,
                onNumberChange = { newString ->
                    editedValue = newString
                },
                onDone = {
                    if(editedValue.isBlank()) {
                        Toast.makeText(
                            context,
                            "Поле швидкості не може бути порожнім!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    if(editedValue.toDouble() == 0.0){
                        Toast.makeText(
                            context,
                            "Швидкість не може дорівнювати 0!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    if(editedValue.toDouble() > 999.9){
                        Toast.makeText(
                            context,
                            "Швидкість повинна бути менше 1000м/с!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@NumberEditGridCell
                    }
                    val newSpeed = editedValue.toDouble()
                    val newEnergy = calculateBulletEnergyUtil(newSpeed, bulletWeight)
                    isEditing = false
                    onEditItem(
                        item.copy(
                            speed = newSpeed,
                            energy = newEnergy
                        )
                    )
                }
            )
        }
        else {
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weights[2]),
                text = String.format("%.1f", item.speed)
            )
        }
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[3]),
            text = String.format("%.3f", item.energy)
        )
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .width(64.dp),
            text = number.toString(),
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
        DropdownMenu(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            expanded = showDropDownMenu,
            onDismissRequest = { showDropDownMenu = false },
            offset = longClickOffset.copy(y = longClickOffset.y - height)
        ) {
            DropdownMenuItem(
                text = {
                    Text("Видалити")
                },
                onClick = {
                    showDropDownMenu = false
                    onDeleteItem(item.shotID)
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Редагувати")
                },
                onClick = {
                    showDropDownMenu = false
                    isEditing = true
                }
            )
        }

    }

}


@Composable
fun TitleRatingCard(
    modifier: Modifier = Modifier,
    isSingleShot: Boolean = true,
    weights: List<Float> = listOf(2f, 2f, 1f, 1f),
    height: Dp = 36.dp
) {
    Row(
        modifier = modifier.height(height)
    ) {
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .width(height),
            text = "№",
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[0]),
            text = "Пристрій"
        )
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[1]),
            text = "Куля"
        )
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[2]),
            text = "Швидкість, м/с"
        )
        Spacer(w = 1.dp)
        TextGridCell(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weights[3]),
            text = "Енергія, Дж"
        )
        if(!isSingleShot){
            Spacer(w = 1.dp)
            EmptyGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

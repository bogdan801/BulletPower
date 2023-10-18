package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.domain.model.ShotRatingItem

@Composable
fun ShotRow(
    modifier: Modifier = Modifier,
    shot: ShotRatingItem? = null,
    number: Int? = null,
    height: Dp = 36.dp,
    isTitle: Boolean = false,
    onDeleteRowClick: () -> Unit = {}
) {
    if(isTitle){
        Row(modifier = modifier
            .fillMaxWidth()
            .height(height)
        ) {
            TextGridCell(
                modifier = Modifier.size(height),
                text = "№",
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                text = "Швидкість, м/с",
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                text = "Енергія, Дж",
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
    else{
        var showDropDownMenu by rememberSaveable {
            mutableStateOf(false)
        }
        var longClickOffset by remember {
            mutableStateOf(DpOffset.Zero)
        }
        val interactionSource = remember {
            MutableInteractionSource()
        }
        Row(modifier = modifier
            .fillMaxWidth()
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
                modifier = Modifier.size(height),
                text = number?.toString() ?: "№",
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                text = String.format("%.1f", shot?.speed ?: 0.0),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                textStyle = MaterialTheme.typography.bodyLarge
            )
            Spacer(w = 1.dp)
            TextGridCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                text = String.format("%.3f", shot?.energy ?: 0.0),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                textStyle = MaterialTheme.typography.bodyLarge
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
                        Text("Видалити постріл")
                    },
                    onClick = {
                        showDropDownMenu = false
                        onDeleteRowClick()
                    }
                )
            }
        }

    }
}
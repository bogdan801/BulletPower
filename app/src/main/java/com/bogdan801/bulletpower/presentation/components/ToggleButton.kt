package com.bogdan801.bulletpower.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    title: String = "",
    icon: ImageVector,
    isToggled: Boolean = false,
    isToggleable: Boolean = true,
    toggledContainerColor: Color = MaterialTheme.colorScheme.surface,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onClick: () -> Unit = {}
) {
    val backgroundColor by animateColorAsState(
        targetValue = if(isToggled) toggledContainerColor else containerColor,
        label = ""
    )
    val outlineColor by animateColorAsState(
        targetValue = if(isToggled) MaterialTheme.colorScheme.outlineVariant else Color.Transparent,
        label = ""
    )
    val iconColor by animateColorAsState(
        targetValue = if(isToggled) MaterialTheme.colorScheme.primary
                      else MaterialTheme.colorScheme.onPrimary,
        label = ""
    )

    Surface(
        modifier = modifier
            .then(
                if(isToggleable){
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick
                    )
                }
                else {
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onClick)
                }
            ),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(
            width = 1.dp,
            color = outlineColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = icon,
                    contentDescription = "",
                    tint = iconColor
                )
            }
            //Spacer(h = 2.dp)
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(h = 8.dp)
        }
    }
}
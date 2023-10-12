package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.domain.model.ShotRatingItem

@Composable
fun ShotRow(
    modifier: Modifier = Modifier,
    shot: ShotRatingItem? = null,
    number: Int? = null,
    height: Dp = 36.dp,
    isTitle: Boolean = false
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
        Row(modifier = modifier
            .fillMaxWidth()
            .height(height)
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
                text = String.format("%.2f", shot?.energy ?: 0.0),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
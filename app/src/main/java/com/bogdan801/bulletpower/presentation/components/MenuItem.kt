package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable BoxScope.() -> Unit,
    height: Dp = 48.dp,
    showDivider: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(56.dp),
            contentAlignment = Alignment.Center
        ){
            icon(this)
        }
        Box(modifier = Modifier.fillMaxSize()) {
            AutoSizeText(
                modifier = Modifier.align(Alignment.CenterStart),
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxTextSize = MaterialTheme.typography.bodyMedium.fontSize,
                minTextSize = MaterialTheme.typography.labelMedium.fontSize
            )
            if(showDivider){
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    thickness = 0.7.dp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}
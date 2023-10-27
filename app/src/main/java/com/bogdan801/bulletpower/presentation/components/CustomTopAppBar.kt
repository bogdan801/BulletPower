package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    isVertical: Boolean = false,
    backButton: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null
){
    if(!isVertical){
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor
                )
            },
            navigationIcon = {
                backButton?.invoke()
            },
            actions = {
                actions?.invoke()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                navigationIconContentColor = contentColor,
                actionIconContentColor = contentColor
            )
        )
    }
    else{
        Column(
            modifier = modifier
                .fillMaxHeight()
                .width(64.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ){
                backButton?.invoke()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ){
                Text(
                    modifier = Modifier
                        .rotate(-90f)
                        .offset(x = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    softWrap = false,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
            Spacer(h = 24.dp)
        }
    }
}
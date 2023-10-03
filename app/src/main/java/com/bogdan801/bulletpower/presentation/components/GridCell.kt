package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TextGridCell(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun EmptyGridCell(
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer
){
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier)
    )
}

@Composable
fun DisplayGridCell(
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    isReadOnly: Boolean = false,
    digitCount: Int = 4,
    dotAfterDigit: Int = 1,
    displaySize: DisplaySize = DisplaySize.Medium,
    value: Double,
    onValueChange: (Double) -> Unit
) {
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        DigitDisplay(
            value = value,
            onValueChange = onValueChange,
            isReadOnly = isReadOnly,
            digitCount = digitCount,
            dotAfterDigit = dotAfterDigit,
            displaySize = displaySize
        )
    }
}

@Composable
fun ButtonGridCell(
    modifier: Modifier,
    title: String,
    icon: (@Composable () -> Unit)? = null,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    titleTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = onClick
    ) {
        Text(
            text = title,
            style = titleTextStyle,
            color = titleTextColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        icon?.invoke()
    }
}
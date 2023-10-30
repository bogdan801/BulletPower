package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.presentation.util.Keyboard
import com.bogdan801.bulletpower.presentation.util.difference
import com.bogdan801.bulletpower.presentation.util.keyboardAsState
import kotlinx.coroutines.delay

@Composable
fun ItemGridCell(
    modifier: Modifier = Modifier,
    itemTitle: String = "",
    itemSubtitle1: String = "",
    itemSubtitle2: String = "",
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
){
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.CenterStart
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            AutoSizeText(
                text = itemTitle,
                style = MaterialTheme.typography.titleMedium,
                color = titleColor,
                maxTextSize = MaterialTheme.typography.titleMedium.fontSize,
                minTextSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Spacer(h = 4.dp)
            AutoSizeText(
                text = "$itemSubtitle1 • $itemSubtitle2",
                style = MaterialTheme.typography.labelMedium,
                color = subtitleColor,
                maxTextSize = MaterialTheme.typography.labelMedium.fontSize,
                minTextSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}

@Composable
fun NumberEditGridCell(
    modifier: Modifier,
    value: String,
    onNumberChange: (newNumber: String) -> Unit,
    onDone: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = true){
        delay(100)
        focusRequester.requestFocus()
    }
    val keyboardState by keyboardAsState()
    LaunchedEffect(key1 = keyboardState){
        delay(700)
        if(keyboardState == Keyboard.Closed){
            focusManager.clearFocus()
            onDone()
        }
    }
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            ),
            onValueChange = { newString ->
                if(newString.text.length <= 8){
                    if(newString.text.length > value.length){
                        val difference = value.difference(newString.text).last()
                        if(difference.isDigit()){
                            onNumberChange(newString.text)
                        }
                        else if(difference == '.'){
                            if(!value.contains(".") && value.isNotBlank()) {
                                onNumberChange(newString.text)
                            }
                        }
                    }
                    else {
                        onNumberChange(newString.text)
                    }
                }
            },
            textStyle = textStyle.copy(
                color = textColor,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onDone()
                }
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun TextGridCell(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    minTextSize: TextUnit = MaterialTheme.typography.labelSmall.fontSize,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        AutoSizeText(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
            maxTextSize = textStyle.fontSize,
            minTextSize = minTextSize
        )
    }
}

@Composable
fun EmptyGridCell(
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer
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
    displayActionState: DisplayActionState? = null,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    isReadOnly: Boolean = false,
    digitCount: Int = 4,
    dotAfterDigit: Int? = 1,
    displaySize: DisplaySize = DisplaySize.Medium,
    shouldCloseKeyboard: Boolean = true,
    value: Double,
    onValueChange: (Double) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(containerColor)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        DigitDisplay(
            value = value,
            displayActionState = displayActionState,
            onValueChange = onValueChange,
            isReadOnly = isReadOnly,
            digitCount = digitCount,
            dotAfterDigit = dotAfterDigit,
            displaySize = displaySize,
            shouldCloseKeyboard = shouldCloseKeyboard
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
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = titleTextStyle,
                color = titleTextColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            icon?.invoke()
        }
    }
}

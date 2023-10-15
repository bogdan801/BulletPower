package com.bogdan801.bulletpower.presentation.components

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.presentation.util.Keyboard
import com.bogdan801.bulletpower.presentation.util.keyboardAsState
import kotlinx.coroutines.launch

@Composable
fun DigitDisplay(
    modifier: Modifier = Modifier,
    displaySize: DisplaySize = DisplaySize.Large,
    digitCount: Int = 4,
    dotAfterDigit: Int? = 1,
    isReadOnly: Boolean = false,
    shouldCloseKeyboard: Boolean = true,
    value: Double = 0.0,
    onValueChange: (Double) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    val cellHeight = when(displaySize){
        DisplaySize.Small -> 42.dp
        DisplaySize.Medium -> 45.dp
        DisplaySize.Large -> 48.dp
    }
    val cellWidth = when(displaySize){
        DisplaySize.Small -> 32.dp
        DisplaySize.Medium -> 35.dp
        DisplaySize.Large -> 38.dp
    }
    val cellSpacing = when(displaySize){
        DisplaySize.Small -> 3.dp
        DisplaySize.Medium -> 4.dp
        DisplaySize.Large -> 6.dp
    }
    val cellTextStyle = when(displaySize){
        DisplaySize.Small -> MaterialTheme.typography.headlineSmall
        DisplaySize.Medium -> MaterialTheme.typography.headlineMedium
        DisplaySize.Large -> MaterialTheme.typography.headlineLarge
    }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    var dynamicDotAfterDigit by remember {
        mutableIntStateOf(dotAfterDigit ?: 1)
    }
    LaunchedEffect(key1 = value){
        dynamicDotAfterDigit = dotAfterDigit ?: value.toString().split(".")[0].length
    }

    var displayState by rememberSaveable {
        mutableStateOf(
            buildString {
                repeat(digitCount){
                    append('0')
                }
            }
        )
    }
    LaunchedEffect(key1 = value){
        displayState = value.toStringDisplay(digitCount, dynamicDotAfterDigit)
    }

    val digitFocusMap = remember { mutableStateOf(mapOf<Int, Boolean>()) }
    LaunchedEffect(key1 = digitFocusMap.value){
        if(!digitFocusMap.value.values.contains(true) && isFocused) {
            isFocused = false
        }
        if(digitFocusMap.value.values.contains(true) && !isFocused){
            isFocused = true
        }
    }

    Row(
        modifier = modifier.height(cellHeight)
    ) {
        repeat(digitCount) { i ->
            if(i == dynamicDotAfterDigit){
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(cellSpacing * 2),
                    contentAlignment = Alignment.BottomCenter
                ){
                    Box(
                        modifier = Modifier
                            .size(cellSpacing)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
            else if(i != 0) Spacer(modifier = Modifier.width(cellSpacing))
            DisplayCell(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(cellWidth)
                    .onFocusChanged {
                        digitFocusMap.value = digitFocusMap.value
                            .toMutableMap()
                            .apply { set(i, it.isFocused) }
                    },
                value = displayState[i],
                onValueChange = { newString ->
                    displayState = buildString {
                        append(displayState)
                        setCharAt(
                            i,
                            if(newString.isBlank()) '0'
                            else newString.last()
                        )
                    }
                    val shouldMoveFocusBack = newString.isBlank() && i != 0
                    if(shouldMoveFocusBack) focusManager.moveFocus(FocusDirection.Previous)

                    val shouldMoveFocusForward = newString.length == 2 && i != (digitCount-1)
                    if(shouldMoveFocusForward) focusManager.moveFocus(FocusDirection.Next)


                    val isLastDigit = newString.length == 2 && i == (digitCount-1)
                    if(isLastDigit){
                        if(shouldCloseKeyboard) {
                            scope.launch {
                                focusManager.clearFocus()
                            }
                            isFocused = false

                        }
                        else{
                            repeat(digitCount - 1){
                                focusManager.moveFocus(FocusDirection.Previous)
                            }
                        }
                    }

                    onValueChange(displayState.toDoubleDisplay(dynamicDotAfterDigit))
                },
                onDone = {
                    focusManager.clearFocus()
                    isFocused = false
                },
                isCellFocused = digitFocusMap.value[i] ?: false,
                isDisplayFocused = isFocused,
                isReadOnly = isReadOnly,
                textStyle = cellTextStyle
            )
            val isKeyboardVisible by keyboardAsState()
            LaunchedEffect(key1 = isKeyboardVisible){
                if(isKeyboardVisible == Keyboard.Closed) {
                    focusManager.clearFocus()
                    isFocused = false
                }
            }
        }
    }
}

enum class DisplaySize {
    Small,
    Medium,
    Large
}

@Composable
fun DisplayCell(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    isCellFocused: Boolean = true,
    isDisplayFocused: Boolean = true,
    isReadOnly: Boolean = true,
    value: Char = '0',
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {}
){
    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if(!isReadOnly && isDisplayFocused) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            val customTextSelectionColors = TextSelectionColors(
                handleColor = Transparent,
                backgroundColor = Transparent,
            )
            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors,
            ) {
                BasicTextField(
                    value = TextFieldValue(
                        text = value.toString(),
                        selection = TextRange(1, 1)
                    ),
                    onValueChange = { newString ->
                        if(newString.text.isBlank()) onValueChange("")
                        else if(newString.text.last().isDigit()) onValueChange(newString.text)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done,
                        autoCorrect = false
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onDone()
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    textStyle = textStyle.copy(
                        color = if(isCellFocused && !isReadOnly) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    ),
                    readOnly = isReadOnly,
                    cursorBrush = SolidColor(Color.Unspecified)
                )
            }
        }
    }
}

private fun Double.toStringDisplay(digitCount: Int, dotAfterDigit: Int) : String {
    val stringRepresentation = toString()
    val splitParts = stringRepresentation.split(".")
    val output = if(splitParts.size == 2) {
        val beforeDot = splitParts[0]
        val afterDot = splitParts[1]
        buildString {
            repeat(digitCount){ i ->
                //before dot
                if(i < dotAfterDigit){
                    val digitToAppend =
                        if ((beforeDot.length - dotAfterDigit + i) in 0..beforeDot.lastIndex) {
                            beforeDot[beforeDot.length - dotAfterDigit + i]
                        }
                        else '0'
                    append(digitToAppend)
                }
                //after dot
                else{
                    val digitToAppend =
                        if((i - dotAfterDigit) in 0..afterDot.lastIndex){
                            afterDot[i - dotAfterDigit]
                        }
                        else '0'
                    append(digitToAppend)
                }
            }
        }
    }
    else {
        val beforeDot = splitParts[0]
        buildString {
            repeat(digitCount) { i ->
                //before dot
                if (i < dotAfterDigit) {
                    val digitToAppend =
                        if ((beforeDot.length - dotAfterDigit + i) in 0..beforeDot.lastIndex) {
                            beforeDot[beforeDot.length - dotAfterDigit + i]
                        }
                        else '0'
                    append(digitToAppend)
                }
                //after dot
                else {
                    append('0')
                }
            }
        }
    }
    return output
}

private fun String.toDoubleDisplay(dotAfterDigit: Int) : Double {
    val currentString = this
    return buildString {
        currentString.forEachIndexed { i, char ->
            if(i == dotAfterDigit) append('.')
            append(char)
        }
    }.toDouble()
}
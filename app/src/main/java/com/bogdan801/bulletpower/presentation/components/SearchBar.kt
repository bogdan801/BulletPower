package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.presentation.util.Keyboard
import com.bogdan801.bulletpower.presentation.util.keyboardAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    placeHolder: String = "Пошук",
    onSearch: (searchQuery: String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardState by keyboardAsState()
    var isFocused by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = keyboardState){
        if(keyboardState == Keyboard.Closed && searchQuery.isBlank()) {
            focusManager.clearFocus()
            isFocused = false
        }
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if(isFocused) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ){
            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    onSearch(it)
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) isFocused = true
                        else {
                            if (searchQuery.isBlank()) isFocused = false
                        }
                    },
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(searchQuery)
                        focusManager.clearFocus()
                    }
                )
            ) {
                TextFieldDefaults.DecorationBox(
                    value = searchQuery,
                    innerTextField = it,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = placeHolder,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    if(searchQuery.isNotBlank()){
                                        onSearch("")
                                        if(keyboardState == Keyboard.Closed) {
                                            focusManager.clearFocus()
                                            isFocused = false
                                        }
                                    }
                                }
                            ),
                            imageVector = if(searchQuery.isNotBlank()) Icons.Default.Clear else Icons.Default.Search,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    enabled = true,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                        top = 0.dp,
                        bottom = 0.dp,
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
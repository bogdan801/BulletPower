package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.bogdan801.bulletpower.presentation.util.difference

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    imeAction: ImeAction = ImeAction.Next,
    type: TextFieldType = TextFieldType.Text,
    charLimit: Int = when(type) {
        TextFieldType.Text -> 30
        TextFieldType.Double -> 9
        TextFieldType.Int -> 9
    }
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    EnableCopyPaste(value = type == TextFieldType.Text){
        TextField(
            modifier = modifier
                .border(
                    color = if (isFocused) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp)
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            value = value,
            onValueChange = { newString ->
                if(newString.length <= charLimit){
                    when(type){
                        TextFieldType.Text -> onValueChange(newString)
                        TextFieldType.Double -> {
                            if(newString.length > value.length){
                                val difference = value.difference(newString).last()
                                if(difference.isDigit()){
                                    onValueChange(newString)
                                }
                                else if(difference == '.'){
                                    if(!value.contains(".") && value.isNotBlank()) {
                                        onValueChange(newString)
                                    }
                                }
                            }
                            else {
                                onValueChange(newString)
                            }
                        }
                        TextFieldType.Int -> {
                            if(newString.isDigitsOnly()) onValueChange(newString)
                        }
                    }
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(4.dp),
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = when(type){
                    TextFieldType.Text -> KeyboardType.Text
                    TextFieldType.Double -> KeyboardType.Decimal
                    TextFieldType.Int -> KeyboardType.Number
                }
            )
        )
    }


}


@Composable
fun EnableCopyPaste(
    value: Boolean = true,
    content: @Composable () -> Unit
) {
    if(!value){
        CompositionLocalProvider(
            LocalTextToolbar provides object : TextToolbar {
                override val status: TextToolbarStatus = TextToolbarStatus.Hidden

                override fun hide() {  }

                override fun showMenu(
                    rect: Rect,
                    onCopyRequested: (() -> Unit)?,
                    onPasteRequested: (() -> Unit)?,
                    onCutRequested: (() -> Unit)?,
                    onSelectAllRequested: (() -> Unit)?,
                ) {
                }
            }
        ) {
            content()
        }
    }
    else content()

}

enum class TextFieldType{
    Text, Double, Int
}
package com.bogdan801.bulletpower.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device

@Composable
fun AddEditDeviceDialogBox(
    showDialog: Boolean,
    title: String = "Додавання пристрою",
    defaultValues: Device = Device(0, "","",0.0),
    saveButtonText: String = "ЗБЕРЕГТИ",
    onDismiss: () -> Unit,
    onSave: (Device) -> Unit = {}
) {
    val context = LocalContext.current
    var name by rememberSaveable {
        mutableStateOf(defaultValues.name)
    }
    var type by rememberSaveable {
        mutableStateOf(defaultValues.type)
    }
    var caliber by rememberSaveable {
        mutableStateOf(
            if(defaultValues.caliber != 0.0) defaultValues.caliber.toString()
            else ""
        )
    }
    LaunchedEffect(key1 = showDialog){
        if(showDialog){
            name = defaultValues.name
            type = defaultValues.type
            caliber = if(defaultValues.caliber != 0.0) defaultValues.caliber.toString()
                      else ""
        }
        else{
            name = ""
            type = ""
            caliber = ""
        }
    }

    BasicDialogBox(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = title
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = name,
                onValueChange = {
                    name = it
                },
                placeholder = "Назва"
            )
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = type,
                onValueChange = {
                    type = it
                },
                placeholder = "Тип"
            )
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = caliber,
                onValueChange = {
                    caliber = it
                },
                placeholder = "Калібр, мм",
                imeAction = ImeAction.Done,
                type = TextFieldType.Double
            )
            Button(
                modifier = Modifier.height(40.dp),
                onClick = {
                    if(name.isBlank()) {
                        Toast.makeText(context, "Ім'я не може бути порожнім", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if(type.isBlank()) {
                        Toast.makeText(context, "Тип не може бути порожнім", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if(caliber.isBlank()) {
                        Toast.makeText(context, "Калібр не може бути 0мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(caliber.toDouble() == 0.0){
                        Toast.makeText(context, "Калібр не може бути 0мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(caliber.toDouble() > 20.0){
                        Toast.makeText(context, "Калібр не може бути більшим за 20мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    onSave(
                        Device(
                            deviceID = defaultValues.deviceID,
                            name = name.trim(),
                            type = type.trim(),
                            caliber = if(caliber.isNotBlank()) caliber.toDouble() else 0.0
                        )
                    )
                    name = ""
                    type = ""
                    caliber = ""
                }
            ) {
                Text(
                    text = saveButtonText,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun AddEditBulletDialogBox(
    showDialog: Boolean,
    title: String = "Додавання кулі",
    defaultValues: Bullet = Bullet(0, "",0.0,0.0),
    saveButtonText: String = "ЗБЕРЕГТИ",
    onDismiss: () -> Unit,
    onSave: (Bullet) -> Unit = {}
) {
    val context = LocalContext.current
    var name by rememberSaveable {
        mutableStateOf(defaultValues.name)
    }
    var weight by rememberSaveable {
        mutableStateOf(
            if(defaultValues.weight != 0.0) defaultValues.weight.toString()
            else ""
        )
    }
    var caliber by rememberSaveable {
        mutableStateOf(
            if(defaultValues.caliber != 0.0) defaultValues.caliber.toString()
            else ""
        )
    }
    LaunchedEffect(key1 = defaultValues){
        name = defaultValues.name
        weight = if(defaultValues.weight != 0.0) defaultValues.weight.toString()
                 else ""
        caliber = if(defaultValues.caliber != 0.0) defaultValues.caliber.toString()
                  else ""
    }
    LaunchedEffect(key1 = showDialog){
        if(showDialog){
            name = defaultValues.name
            weight = if(defaultValues.weight != 0.0) defaultValues.weight.toString()
            else ""
            caliber = if(defaultValues.caliber != 0.0) defaultValues.caliber.toString()
            else ""
        }
        else{
            name = ""
            weight = ""
            caliber = ""
        }
    }

    BasicDialogBox(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = title
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = name,
                onValueChange = {
                    name = it
                },
                placeholder = "Назва"
            )
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = weight,
                onValueChange = {
                    weight = it
                },
                placeholder = "Вага, г",
                type = TextFieldType.Double
            )
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                value = caliber,
                onValueChange = {
                    caliber = it
                },
                placeholder = "Калібр, мм",
                imeAction = ImeAction.Done,
                type = TextFieldType.Double
            )
            Button(
                modifier = Modifier.height(40.dp),
                onClick = {
                    if(name.isBlank()) {
                        Toast.makeText(context, "Ім'я не може бути порожнім", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if(weight.isBlank()) {
                        Toast.makeText(context, "Вага кулі не може бути 0гр", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(weight.toDouble() == 0.0){
                        Toast.makeText(context, "Вага кулі не може бути 0гр", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(weight.toDouble() > 9.9999){
                        Toast.makeText(context, "Вага кулі не може бути більша за 10гр", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if(caliber.isBlank()) {
                        Toast.makeText(context, "Калібр не може бути 0мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(caliber.toDouble() == 0.0){
                        Toast.makeText(context, "Калібр не може бути 0мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(caliber.toDouble() > 20.0){
                        Toast.makeText(context, "Калібр не може бути більшим за 20мм", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    onSave(
                        Bullet(
                            bulletID = defaultValues.bulletID,
                            name = name.trim(),
                            weight = if(weight.isNotBlank()) weight.toDouble() else 0.0,
                            caliber = if(caliber.isNotBlank()) caliber.toDouble() else 0.0
                        )
                    )
                    name = ""
                    weight = ""
                    caliber = ""
                }
            ) {
                Text(
                    text = saveButtonText,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    title: String = "",
    subtitle: String = "",
    confirmButtonText: String = "Видалити",
    cancelButtonText: String = "Скасувати",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    BasicDialogBox(showDialog, onDismiss, title){
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ){
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, end = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(32.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = cancelButtonText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(w = 8.dp)
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(
                        text = confirmButtonText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun BasicDialogBox(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String = "",
    width: Dp = 350.dp,
    cornerRadius: Dp = 12.dp,
    content: @Composable (BoxScope.()->Unit)? = null
) {
    if(showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .width(width)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .clip(RoundedCornerShape(cornerRadius)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()){
                    content?.invoke(this)
                }
            }
        }
    }
}
package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan801.bulletpower.R
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device

@Composable
fun SelectorButton(
    modifier: Modifier = Modifier,
    selectionItem: SelectionItem,
    selectedDevice: Device? = null,
    selectedBullet: Bullet? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(selectionItem.drawableID), 
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        AutoSizeText(
            text = when(selectionItem){
                SelectionItem.Device -> {
                    if(selectedDevice == null) "Пристрій"
                    else "${selectedDevice.name} ${selectedDevice.type}"
                }
                SelectionItem.Bullet -> {
                    if(selectedBullet == null) "Куля"
                    else "${selectedBullet.name} ${selectedBullet.caliber}мм"
                }
            },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            maxTextSize = MaterialTheme.typography.titleMedium.fontSize,
            minTextSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

enum class SelectionItem(val drawableID: Int){
    Device(R.drawable.ic_gun),
    Bullet(R.drawable.ic_bullet)
}
package com.bogdan801.bulletpower.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Spacer(
    @SuppressLint("ModifierParameter") modifier: Modifier?  = null,
    w: Dp = 0.dp,
    h: Dp = 0.dp,
    background: Color = Color.Transparent
) {
    if(modifier != null){
        Spacer(
            modifier = modifier
        )
    }
    if(background == Color.Transparent){
        Spacer(
            modifier = Modifier
                .width(w)
                .height(h)
        )
    }
    else {
        Box(
            modifier = Modifier
                .then(
                    if (w == 0.dp && h > 0.dp) Modifier
                        .fillMaxWidth()
                        .height(h)
                    else Modifier
                )
                .then(
                    if (h == 0.dp && w > 0.dp) Modifier
                        .fillMaxHeight()
                        .width(w)
                    else Modifier
                )
                .background(background)
        )
    }

}
package com.kaiku.composecomponent.component.button

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun DrawableVectorIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int? = null,
    imageVector: ImageVector? = null,
    tint: Color,
) {
    drawableRes?.let { res ->
        Icon(
            modifier = modifier,
            painter = painterResource(id = res),
            contentDescription = "simple_icon_button_by_drawable",
            tint = tint
        )
    } ?: imageVector?.let { vector ->
        Icon(
            modifier = modifier,
            imageVector = vector,
            contentDescription = "simple_icon_button_by_vector",
            tint = tint
        )
    }
}
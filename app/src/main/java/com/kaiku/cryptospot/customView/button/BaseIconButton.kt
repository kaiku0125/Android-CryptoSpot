package com.kaiku.cryptospot.customView.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BaseIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int,
    tint : Color = Color.Unspecified,
    iconSize : Dp = 17.dp,
    onIconClick: () -> Unit
) {
    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier.fillMaxSize().size(iconSize),
            onClick = { onIconClick.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = drawableRes),
                contentDescription = "simple_icon_button_by_drawable",
                tint = tint
            )
        }
    }
}


@Composable
fun BaseIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint : Color = Color.Unspecified,
    iconSize : Dp = 17.dp,
    onIconClick: () -> Unit
) {
    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier.fillMaxSize().size(iconSize),
            onClick = { onIconClick.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = imageVector,
                contentDescription = "simple_icon_button_by_drawable",
                tint = tint
            )
        }
    }
}
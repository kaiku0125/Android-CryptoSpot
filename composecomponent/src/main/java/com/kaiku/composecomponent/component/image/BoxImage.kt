package com.kaiku.composecomponent.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kaiku.composecomponent.extension.clickableEffectConfig

@Composable
fun BoxImage(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    @DrawableRes drawable: Int,
    onClick: (() -> Unit)? = null
) {
    val mModifier = remember {
        if (onClick != null) {
            modifier.clickableEffectConfig(
                onClick = {
                    onClick.invoke()
                }
            )
        } else {
            modifier
        }
    }

    Box(
        modifier = mModifier,
        contentAlignment = alignment
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = null
        )
    }
}
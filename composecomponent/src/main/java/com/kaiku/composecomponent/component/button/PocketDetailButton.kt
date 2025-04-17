package com.kaiku.composecomponent.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.sdp

@Composable
fun PocketDetailButton(
    modifier: Modifier = Modifier,
    textConfig: PocketTextConfig,
    backgroundColor: Color = color_414141,
    onClick: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(100.sdp()),
        modifier = Modifier.scale(if (textConfig.isEnable) scale else 1f)
    ) {
        Row(
            modifier = modifier
                .clip(shape = RoundedCornerShape(100.sdp()))
                .clickableEffectConfig(
                    config = rememberClickableConfig(clickEffect = false),
                    onScaling = { scale = it },
                    onClick = onClick
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PocketSpacer(width = 8)
            PocketText(config = textConfig)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}
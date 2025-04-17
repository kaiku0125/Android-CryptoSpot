package com.kaiku.composecomponent.component.radio

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp

/**
 * 單純的radio元件
 *
 * @param drawableProvider 依據各平台實作的 DrawableProvider
 * @param isChecked 是否勾選
 * @param isEnabled 是否可被點擊
 * @param tint icon顏色
 * @param iconSize icon的大小
 */
@Composable
fun PocketRadioIconComponent(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    isChecked: Boolean,
    isEnabled: Boolean,
    tint: Color = Color.Unspecified,
    iconSize: Dp = 50.sdp()
) {
    Box(
        modifier = modifier.size(iconSize),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isChecked,
            label = ""
        ) { stateChecked ->
            if (stateChecked) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(
                        id = if (isEnabled) {
                            drawableProvider.checkOn
                        } else {
                            drawableProvider.checkOff
                        }
                    ),
                    tint = tint,
                    contentDescription = ""
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(3.sdp())
                        .border(
                            border = BorderStroke(
                                width = 1.sdp(),
                                color = if (isEnabled) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    color_9e9e9f
                                }
                            ),
                            shape = CircleShape
                        )
                )
            }
        }

    }

}




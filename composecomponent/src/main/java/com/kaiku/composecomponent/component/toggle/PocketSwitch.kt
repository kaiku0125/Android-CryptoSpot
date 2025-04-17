package com.kaiku.composecomponent.component.toggle

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_979797
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp

/**
 * 口袋 Switch 元件
 *
 * @param checked 開關狀態
 * @param size 開關大小
 * @param switchWidth 開關背景大小
 * @param padding 開關本身對於背景的padding
 * @param parentShape 開關背景形狀
 * @param toggleShape 開關本身形狀
 * @param animationSpec 開關動畫
 * @param needIcons 是否需要開關
 * @param switchOnIcon 開啟時顯示的icon
 * @param switchOffIcon 關閉時顯示的icon
 * @param borderWidth 開關外誆
 * @param isEnabled 開關是否可被點擊
 * @param onClick export 開關切換事件
 */

@Composable
fun PocketSwitch(
    checked: Boolean = false,
    size: Dp = 50.sdp(),
    switchWidth: Dp = size * 33 / 20,
    padding: Dp = size / 20,
    parentShape: RoundedCornerShape = CircleShape,
    toggleShape: RoundedCornerShape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    needIcons: Boolean = false,
    iconSize: Dp = size / 3,
    switchOnIcon: ImageVector = Icons.Rounded.Done,
    switchOffIcon: ImageVector = Icons.Rounded.Clear,
    borderWidth: Dp = 1.sdp(),
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (checked) (switchWidth - size) else 0.dp,
        animationSpec = animationSpec,
        label = ""
    )

    Box(
        modifier = Modifier
            .width(switchWidth)
            .height(size)
            .clip(shape = parentShape)
            .clickableEffectConfig(
                config = ClickableConfig(
                    needHaptic = false
                ),
                onClick = {
                    if (isEnabled) onClick()
                }
            )
            .background(
                if (checked && isEnabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    color_979797
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(Color.White)
        )

        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = parentShape
                )
        ) {
            if (needIcons) {
                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = switchOffIcon,
                        contentDescription = "Theme Icon",
                        tint = if (checked) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.primary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(size)
                        .padding(end = size * 5 / 20),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = switchOnIcon,
                        contentDescription = "Theme Icon",
                        tint = if (checked) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        }

    }
}



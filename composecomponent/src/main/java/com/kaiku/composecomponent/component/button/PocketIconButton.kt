package com.kaiku.composecomponent.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberIconClickableConfig
import com.kaiku.composecomponent.utils.sdp

/**
 * 用 Box 來包裝 IconButton 達成佈局便利性
 *
 * @param drawableRes Icon 的Resource ID 兩者擇一
 * @param imageVector Icon 的 Vector 兩者擇一
 * @param tint Icon顏色
 * @param iconSize Icon漣漪大小(若要設定icon大小 ➔ iconModifier.size()做設定)
 * @param isEnable 是否可被點擊
 * @param clickableConfig Icon點擊設定(不需要特別設定，除非想要移除漣漪效果 ➔ 使用ClickableConfig.DEFAULT)
 * @param onIconClick Icon點擊事件
 */
@Composable
fun PocketIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int? = null,
    imageVector: ImageVector? = null,
    tint: Color = Color.Unspecified,
    iconSize: Dp = 24.sdp(),
    isEnable: Boolean = true,
    clickableConfig: ClickableConfig = rememberIconClickableConfig(
        isEnable = isEnable,
        iconSize = iconSize
    ),
    onIconClick: () -> Unit
) {
    val mModifier = remember(clickableConfig, isEnable, onIconClick) {
        derivedStateOf {
            if (clickableConfig.bounded) {
                modifier.clip(CircleShape)
            } else {
                modifier
            }.clickableEffectConfig(
                config = clickableConfig,
                onClick = {
                    if (isEnable) {
                        onIconClick.invoke()
                    }
                }
            )
        }
    }.value

    Box(
        modifier = mModifier,
        contentAlignment = Alignment.Center
    ) {
        DrawableVectorIcon(
            modifier = iconModifier,
            drawableRes = drawableRes,
            imageVector = imageVector,
            tint = tint
        )
    }
}

/**
 * @param imageResource Icon的Resource ID 兩者擇一
 * @param imageVector Icon的Vector 兩者擇一
 * @param tint Icon顏色
 * @param iconSize Icon漣漪大小
 * @param isExpand 是否展開
 * @param clickableConfig 點擊效果
 * @param onIconClick export 點擊事件
 */
@Composable
fun PocketRotationIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes imageResource: Int? = null,
    imageVector: ImageVector = Icons.Default.KeyboardArrowDown,
    tint: Color = Color.Unspecified,
    iconSize: Dp = 17.sdp(),
    isExpand: Boolean,
    clickableConfig: ClickableConfig = rememberIconClickableConfig(iconSize = iconSize),
    onIconClick: () -> Unit,
) {
    var rotateAnimationAngle by remember {
        mutableFloatStateOf(0f)
    }

    val rotateAnimation = animateFloatAsState(
        targetValue = rotateAnimationAngle,
        animationSpec = tween(
            easing = LinearEasing
        ), label = ""
    )

    LaunchedEffect(key1 = isExpand) {
        rotateAnimationAngle = if (isExpand) {
            180f
        } else {
            0f
        }
    }

    PocketIconButton(
        modifier = modifier.graphicsLayer { rotationZ = rotateAnimation.value },
        drawableRes = imageResource,
        imageVector = imageVector,
        iconSize = iconSize,
        tint = tint,
        clickableConfig = clickableConfig,
        onIconClick = onIconClick
    )
}
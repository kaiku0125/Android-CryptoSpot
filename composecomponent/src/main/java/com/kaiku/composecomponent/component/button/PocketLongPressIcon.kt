package com.kaiku.composecomponent.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.utils.sdp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// TODO: 某些 android 版本 會沒辦法單次點擊
/**
 * 長按按鈕元件
 *
 * @param drawableRes Icon 的Resource ID 兩者擇一
 * @param imageVector Icon 的 Vector 兩者擇一
 * @param isEnabled Icon是否可被點擊
 * @param tint Icon顏色
 * @param size Icon大小
 * @param longPressDelay 長按延遲
 * @param longPressInterval 長按觸發事件間隔
 * @param onClick export Icon點擊事件
 */
@Composable
fun PocketLongPressIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int? = null,
    imageVector: ImageVector? = null,
    isEnabled: Boolean = true,
    tint: Color = Color.White,
    size: Dp = 24.sdp(),
    longPressDelay: Long = 800,
    longPressInterval: Long = 100,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    // pointerInput(Unit) 會 remember lambda，導致外面的onclick事件會異常
    val currentOnClick by rememberUpdatedState(onClick)

    var isPressActive by remember { mutableStateOf(false) }
    var clickJob by remember { mutableStateOf<Job?>(null) }

    val aniTintColor: Color by animateColorAsState(
        targetValue = if (isEnabled) {
            tint
        } else {
            color_717071
        },
        label = ""
    )
    val aniRippleColor: Color by animateColorAsState(
        targetValue = if (isPressActive && isEnabled) {
            color_717071.copy(alpha = 0.5f)
        } else {
            Color.Transparent
        },
        label = ""
    )

    LaunchedEffect(isEnabled) {
        if (isEnabled.not()) {
            isPressActive = false
            clickJob?.cancel()
        }
    }

    LaunchedEffect(isPressActive) {
        if (isPressActive) {
            clickJob?.cancel()
            clickJob = launch {
                delay(longPressDelay)
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                while (isActive) {
                    onClick.invoke()
                    delay(longPressInterval)
                }
            }
        } else {
            clickJob?.cancel()
        }
    }

    val mModifier = if (isEnabled) {
        modifier
            .size(size * 1.2f)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()

                    isPressActive = true

                    do {
                        val event: PointerEvent = awaitPointerEvent()

                        // Consuming event prevents other gestures or scroll to intercept
                        event.changes.forEach { pointerInputChange: PointerInputChange ->
                            if (isOutOffRange(pointerInputChange)) {
                                pointerInputChange.consume()
                                isPressActive = false
                            } else {
                                if (pointerInputChange.changedToUp() && isPressActive) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    currentOnClick()
                                }
                            }

                        }
                    } while (event.changes.any { it.pressed })

                    isPressActive = false
                }
            }
            .background(aniRippleColor, CircleShape)
    } else {
        modifier
            .size(size * 1.2f)
            .background(aniRippleColor, CircleShape)
    }

    Box(
        modifier = mModifier,
        contentAlignment = Alignment.Center
    ) {
        DrawableVectorIcon(
            modifier = Modifier.size(size),
            drawableRes = drawableRes,
            imageVector = imageVector,
            tint = aniTintColor
        )
    }
}

private fun isOutOffRange(change: PointerInputChange): Boolean {
    val changeX = change.positionChange().x.absoluteValue
    val changeY = change.positionChange().y.absoluteValue
    return changeX > 10f || changeY > 10f
}
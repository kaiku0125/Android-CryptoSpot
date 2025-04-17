package com.kaiku.composecomponent.component.checkboxfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.utils.sdp

@Composable
fun PocketCheckBoxComponent(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    size: Dp = 24.sdp(),
    isEnable: Boolean = true,
    shape: Shape = RoundedCornerShape(4.sdp()),
    borderThickness: Dp = 0.dp,
    borderColor: Color = Color.White,
    duration: Int = 500,
    checkColor: Color = Color.White,
    checkedBgColor: Color = MaterialTheme.colorScheme.primary,
    unCheckedBgColor: Color = Color.White,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor by animateColorAsState(
        targetValue = if (isEnable) {
            if (isChecked) checkedBgColor else unCheckedBgColor
        } else {
            color_717071
        },
        animationSpec = tween(duration),
        label = ""
    )
    val density = LocalDensity.current

    Row(
        modifier = if (isEnable) {
            modifier.toggleable(
                value = isChecked,
                role = androidx.compose.ui.semantics.Role.Checkbox,
                onValueChange = onValueChange
            )
        } else modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color = checkboxColor, shape = shape)
                .border(width = borderThickness, color = borderColor, shape = shape),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) {
                        (size.value * -0.5).dp.roundToPx()
                    }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration),
                    initialWidth = {
                        with(density) {
                            (size.value * -0.5).dp.roundToPx()
                        }
                    }
                ),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier.size(size),
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = checkColor
                )
            }
        }
    }
}
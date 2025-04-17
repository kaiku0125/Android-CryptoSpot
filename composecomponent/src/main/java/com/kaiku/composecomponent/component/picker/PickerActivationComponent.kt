package com.kaiku.composecomponent.component.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.component.dialog.PickerDialog
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp

@Composable
fun PickerActivationComponent(
    modifier: Modifier = Modifier,
    value: Int,
    range: Iterable<Int> = -10..10,
    isEnabled: Boolean = true,
    textConfig: PocketTextConfig = PocketTextConfig(),
    onValueSelected: (Int) -> Unit
) {
    val isDialogVisible = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(80.sdp())
            .height(30.sdp())
            .clip(RoundedCornerShape(8.sdp()))
            .background(
                if (isEnabled) {
                    color_717071
                } else {
                    color_414141
                }
            )
            .clickableEffectConfig(
                config = ClickableConfig(
                    needRipple = false
                ),
                onClick = {
                    if (isEnabled) isDialogVisible.value = true
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.sdp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketText(
                modifier = Modifier.weight(3f),
                config = textConfig
            )
            Icon(
                modifier = Modifier.weight(1f).size(24.sdp()),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "",
                tint = if (isEnabled) Color.White else color_717071
            )
        }
    }

    PickerDialog(
        isVisible = isDialogVisible.value,
        state = value,
        range = range,
        onConfirmed = {
            isDialogVisible.value = false
            onValueSelected.invoke(it.toInt())
        },
        onDismiss = {
            isDialogVisible.value = false
        }

    )
}
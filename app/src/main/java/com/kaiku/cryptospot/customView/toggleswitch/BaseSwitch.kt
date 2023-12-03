package com.kaiku.cryptospot.customView.toggleswitch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.toggleswitch.data.SwitchConfig
import com.kaiku.cryptospot.extension.clickableEffectConfig
import com.kaiku.cryptospot.extension.data.ClickableConfig
import com.kaiku.cryptospot.presentation.theme.color_979797

/**
 * @sample BaseSwitch 基本開關元件
 *
 * @param config 基本開關元件設定
 * @param onClick export點擊事件
 */

@Composable
fun BaseSwitch(
    config: SwitchConfig = SwitchConfig(),
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (config.checked) (config.switchWidth - config.size) else 0.dp,
        animationSpec = config.animationSpec,
        label = ""
    )

    Box(
        modifier = Modifier
            .width(config.switchWidth)
            .height(config.size)
            .clip(shape = config.parentShape)
            .clickableEffectConfig(
                config = ClickableConfig(
                    needHaptic = false
                ),
                onClick = onClick
            )
            .background(
                if (config.checked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    color_979797
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(config.size)
                .offset(x = offset)
                .padding(all = config.padding)
                .clip(shape = config.toggleShape)
                .background(Color.White)
        )

        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = config.borderWidth,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = config.parentShape
                )
        ) {
            if (config.needIcons) {
                Box(
                    modifier = Modifier.size(config.size),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(config.iconSize),
                        imageVector = config.switchOffIcon,
                        contentDescription = "Theme Icon",
                        tint = if (config.checked) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.primary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(config.size)
                        .padding(end = config.size * 5 / 20),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(config.iconSize),
                        imageVector = config.switchOnIcon,
                        contentDescription = "Theme Icon",
                        tint = if (config.checked) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PocketSwitchPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BaseSwitch(
            config = SwitchConfig(
                checked = false,
                size = 100.dp
            ),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseSwitch(
            config = SwitchConfig(
                checked = false,
                size = 35.dp,
                padding = 3.dp
            ),
            onClick = {

            }
        )
    }
}



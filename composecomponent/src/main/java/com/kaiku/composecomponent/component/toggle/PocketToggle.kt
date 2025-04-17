package com.kaiku.composecomponent.component.toggle

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig

data class PocketToggleConfig(
    val isTurnedOn: Boolean = false,
    val alignment: Alignment = Alignment.Center,
    val clickableConfig: ClickableConfig = ClickableConfig()
)

@Composable
fun PocketToggle(
    modifier: Modifier = Modifier,
    config: PocketToggleConfig = PocketToggleConfig(),
    turnOnContent: @Composable (() -> Unit)? = {},
    turnOffContent: @Composable (() -> Unit)? = {},
    onToggle: (Boolean) -> Unit = {}
) {
    Box(
        modifier = modifier.clickableEffectConfig(
            config = config.clickableConfig,
            onClick = {
                onToggle(config.isTurnedOn.not())
            }
        ),
        contentAlignment = Alignment.Center,
        content = {
            Crossfade(
                targetState = config.isTurnedOn,
                label = "onToggleLabel"
            ) { toggleState ->
                if (toggleState) {
                    turnOnContent?.invoke()
                } else {
                    turnOffContent?.invoke()
                }
            }

        }
    )
}

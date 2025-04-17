package com.kaiku.composecomponent.component.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp

@Preview
@Composable
fun CircularLoadingScene(
    modifier: Modifier = Modifier,
    descriptionConfig: PocketTextConfig? = null,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.fillMaxSize().clickableEffectConfig(
            config = ClickableConfig(
                needRipple = false,
                needSound = false
            ),
            onClick = {}
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = color
            )
            Spacer(modifier = Modifier.height(8.sdp()))
            descriptionConfig?.let { config ->
                PocketText(
                    config = config
                )
            }
        }
    }
}
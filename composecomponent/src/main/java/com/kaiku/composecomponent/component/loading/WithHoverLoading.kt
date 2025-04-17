package com.kaiku.composecomponent.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.text17Sp

@Composable
fun WithHoverLoading(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    alpha : Float = 0.5f,
    descriptionConfig: PocketTextConfig = PocketTextConfig(
        value = "載入中...",
        style = text17Sp(500),
        textColor = MaterialTheme.colorScheme.primary
    ),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content.invoke()

        if (isLoading) {
            CircularLoadingScene(
                modifier = Modifier.background(
                    Color.Black.copy(
                        alpha = alpha
                    )
                ),
                descriptionConfig = descriptionConfig
            )
        }
    }
}
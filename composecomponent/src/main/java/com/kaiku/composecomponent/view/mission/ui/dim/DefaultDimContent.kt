package com.kaiku.composecomponent.view.mission.ui.dim

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.text17Sp

@Composable
internal fun DefaultDimContent(
    modifier: Modifier = Modifier
) {
    PocketText(
        modifier = modifier.fillMaxSize(),
        config = PocketTextConfig(
            value = "任務達成",
            style = text17Sp(500),
            maxLines = 1
        )
    )
}
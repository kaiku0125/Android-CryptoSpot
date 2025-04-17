package com.kaiku.composecomponent.component.loading

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.github.fengdai.compose.pulltorefresh.PullToRefreshState
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text17Sp


@Composable
fun PocketPullRefreshIndicator(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    refreshTriggerDistance: Dp,
    refreshingOffset: Dp
) {
    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }
    val indicatorHeight = 48.sdp()
    val indicatorHeightPx = with(LocalDensity.current) { indicatorHeight.toPx() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(end = 26.sdp())
            .graphicsLayer {
                translationY = state.contentOffset - (refreshingOffsetPx + indicatorHeightPx) / 2
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(
            targetState = state.isRefreshing,
            label = ""
        ) { refresh ->
            if (refresh) {
                PocketCircularProgressIndicator(
                    indicatorModifier = Modifier
                        .padding(end = 8.sdp())
                        .size(18.sdp())
                )
            } else {
                val progress = ((state.contentOffset - refreshTriggerPx / 2) / refreshTriggerPx * 2)
                    .coerceIn(0f, 1f)
                Image(
                    painter = painterResource(id = R.drawable.lib_pocket_pull_refresh_arrow),
                    contentDescription = "pull to refresh",
                    modifier = Modifier
                        .padding(end = 8.sdp())
                        .size(18.sdp())
                        .rotate(progress * 180),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        }

        AnimatedPocketText(
            config = PocketTextConfig(
                value = when {
                    state.isPullInProgress && state.contentOffset >= refreshTriggerPx -> "釋放重新整理"
                    state.isRefreshing -> "載入中..."
                    else -> "下拉重新整理"
                },
                style = text17Sp(600),
                textColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
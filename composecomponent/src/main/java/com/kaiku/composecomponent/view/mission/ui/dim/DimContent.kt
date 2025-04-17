package com.kaiku.composecomponent.view.mission.ui.dim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kaiku.composecomponent.theme_color_primary_tw
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithBottomLine
import com.kaiku.composecomponent.theme_color_primary_us
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.MissionStringRes

// 任務達成 && 獎勵為立即發放
@Composable
internal fun DimContent(
    modifier: Modifier = Modifier,
    isTw: Boolean,
    stringRes: MissionStringRes,
    onGotoMyCoupon: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PocketText(
            modifier = Modifier,
            config = PocketTextConfig(
                value = stringRes.missionCompleted,
                style = text17Sp(500),
                maxLines = 1
            )
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PocketText(
                modifier = Modifier,
                config = PocketTextConfig(
                    value = "獎勵已發送至【",
                    style = text15Sp(),
                    maxLines = 1
                )
            )
            PocketTextWithBottomLine(
                config = PocketTextConfig(
                    value = stringRes.missionCompletedSendCoupon,
                    style = text15Sp(),
                    textColor = if (isTw) {
                        theme_color_primary_tw
                    } else {
                        theme_color_primary_us
                    },
                    maxLines = 1
                ),
                onClick = onGotoMyCoupon
            )
            PocketText(
                modifier = Modifier,
                config = PocketTextConfig(
                    value = "】",
                    style = text15Sp()
                )
            )
        }
    }
}
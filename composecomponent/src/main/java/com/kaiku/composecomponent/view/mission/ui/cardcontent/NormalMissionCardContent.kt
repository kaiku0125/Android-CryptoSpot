package com.kaiku.composecomponent.view.mission.ui.cardcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.loading.HorizontalProgressComponent
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.theme_color_primary_tw
import com.kaiku.composecomponent.theme_color_primary_us
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionStringRes

// 一般任務主要內容(獎勵 + 進度條)
@Composable
internal fun NormalMissionCardContent(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    cardState: MissionCardState = MissionCardState()
) {
    val rewards = cardState.rewards
    Column(
        modifier = modifier.padding(horizontal = 12.sdp(), vertical = 8.sdp())
    ) {
        Row {
            PocketText(
                modifier = Modifier.heightIn(min = 18.sdp()),
                config = PocketTextConfig(
                    value = "${stringRes.missionListReward}：\t",
                    style = text13Sp()
                )
            )
            Column {
                val size = rewards.size
                repeat(rewards.size) { index ->
                    val indexText = when (size) {
                        0 -> "-"
                        1 -> ""
                        else -> "${(index + 1)}.\t"
                    }
                    PocketText(
                        modifier = Modifier.heightIn(min = 18.sdp()),
                        config = PocketTextConfig(
                            value = indexText + rewards[index].name,
                            style = text13Sp(),
                            textColor = Color.White,
                            maxLines = Int.MAX_VALUE
                        )
                    )
                    if (rewards.lastIndex != index) {
                        PocketSpacer(height = 4)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.sdp()))
        PocketText(
            modifier = Modifier.height(18.sdp()),
            config = PocketTextConfig(
                value = "※ ${cardState.rewardDescription}",
                style = text13Sp(),
                textColor = color_9e9e9f
            )
        )
        Spacer(modifier = Modifier.height(10.sdp()))

        cardState.targets.forEachIndexed { index, target ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                PocketText(
                    config = PocketTextConfig(
                        value = target.type.title,
                        style = text13Sp()
                    )
                )
                Spacer(modifier = Modifier.width(8.sdp()))
                HorizontalProgressComponent(
                    modifier = Modifier.height(20.sdp()),
                    percentage = target.nowStep.toFloat() / target.totalStep.toFloat(),
                    total = target.totalStep,
                    progressColor = if (cardState.isTw) {
                        theme_color_primary_tw
                    } else {
                        theme_color_primary_us
                    }
                )
            }
            if (cardState.targets.lastIndex != index) {
                Spacer(modifier = Modifier.height(8.sdp()))
            }
        }
    }
}

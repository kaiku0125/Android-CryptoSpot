package com.kaiku.composecomponent.view.mission.ui.cardcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.kaiku.composecomponent.view.mission.data.MissionTargetType

@Composable
internal fun MgmMissionCardContent(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    cardState: MissionCardState
) {
    val targets = cardState.targets
    val rewards = cardState.rewards

    // 獲取已領獎勵
    val mReceivedRewardTimes = rewards.firstOrNull()?.receiveReward.toNumberFormat(
        invalidText = "--"
    )

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
                            textColor = Color.White
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

        if (MissionTargetType.isMgmOthers(targets)) {
            OtherUserContent(
                cardState = cardState,
                stringRes = stringRes
            )
        } else {
            UserSelfContent(cardState = cardState)
        }

        Spacer(modifier = Modifier.height(10.sdp()))

        PocketText(
            config = PocketTextConfig(
                value = "${stringRes.missionMgmReceivedCountTitle}：$mReceivedRewardTimes",
                style = text13Sp(),
            )
        )
    }
}

/**
 * 若為用戶自己的任務時
 */
@Composable
private fun UserSelfContent(cardState: MissionCardState) {
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


/**
 * 若為用戶分享對象相關的任務時
 */
@Composable
private fun OtherUserContent(
    cardState: MissionCardState,
    stringRes: MissionStringRes
) {
    // 符合條件人數
    val matchCount = cardState.mgmCount.toNumberFormat(
        invalidText = "--"
    )

    // 達成目標人數 (為何是total? 因為此欄位沒有limit限制)
    val totalAchieveTargetCount = cardState.rewards.firstOrNull()?.totalAchieveCount.toNumberFormat(
        invalidText = "--"
    )
    
    PocketText(
        config = PocketTextConfig(
            value = "${stringRes.missionMgmMatchCountTitle}：$matchCount",
            style = text13Sp(),
        )
    )
    Spacer(modifier = Modifier.height(10.sdp()))
    PocketText(
        config = PocketTextConfig(
            value = "${stringRes.missionMgmAchieveCountTitle}：$totalAchieveTargetCount",
            style = text13Sp(),
        )
    )

}
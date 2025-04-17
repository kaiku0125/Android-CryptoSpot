package com.kaiku.composecomponent.view.mission.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_gift_bg_tw
import com.kaiku.composecomponent.color_gift_bg_us
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.dialog.PocketComposeDialog
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionReward
import com.kaiku.composecomponent.view.mission.data.MissionStringRes

@Composable
fun CheckRewardDialog(
    stringRes: MissionStringRes = MissionStringRes.Default,
    cardState: MissionCardState,
    reward: MissionReward,
    onDismiss: () -> Unit
) {

    val text = buildString {
        append("【${reward.name}】${reward.number} ${reward.unit}")
    }

    PocketComposeDialog(
        primaryColor = cardState.getPrimaryColor(),
        title = stringRes.missionCheckRewardDialogTitle,
        positiveText = "關閉",
        contentPaddingVertical = 28,
        negativeButton = null,
        positiveButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PocketPrimaryButton(
                    modifier = Modifier
                        .width(120.sdp())
                        .height(35.sdp()),
                    config = PocketTextConfig(
                        value = "關閉",
                        style = text17Sp(500),
                        maxLines = 1
                    ),
                    border = BorderStroke(0.dp, Color.Transparent),
                    primaryColor = cardState.getPrimaryColor(),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        onDismiss.invoke()
                    }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(60.sdp())
                        .clip(CircleShape)
                        .background(
                            if (cardState.isTw) {
                                color_gift_bg_tw
                            } else {
                                color_gift_bg_us
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(40.sdp()),
                        painter = painterResource(id = R.drawable.pocket_ic_mission_gift),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.sdp()))
                PocketText(
                    config = PocketTextConfig(
                        value = stringRes.missionCheckRewardDialogContentStart,
                        style = text15Sp(),
                        maxLines = 1
                    )
                )
                Spacer(modifier = Modifier.height(8.sdp()))
                PocketText(
                    config = PocketTextConfig(
                        value = text,
                        style = text15Sp(),
                        maxLines = Int.MAX_VALUE
                    )
                )
                Spacer(modifier = Modifier.height(8.sdp()))
                PocketText(
                    config = PocketTextConfig(
                        value = stringRes.missionCheckRewardDialogContentEnd,
                        style = text15Sp(),
                        maxLines = 1
                    )
                )
                Spacer(modifier = Modifier.height(28.sdp()))
            }
        }
    )

}

@Preview
@Composable
private fun CheckRewardDialogPreview() {
    CheckRewardDialog(
        cardState = MissionCardState(),
        reward = MissionReward.PREVIEW_DIALOG,
        onDismiss = {}
    )
}
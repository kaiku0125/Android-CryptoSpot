package com.kaiku.composecomponent.view.mission.ui.dim

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.replaceArg
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.CouponState
import com.kaiku.composecomponent.view.mission.data.CouponState.Companion.toCouponUnit
import com.kaiku.composecomponent.view.mission.data.MissionCardState
import com.kaiku.composecomponent.view.mission.data.MissionStringRes

// 獎勵為指定日期發放
@Composable
internal fun DimContentWhenMissionFinished(
    modifier: Modifier = Modifier,
    date : String,
    stringRes: MissionStringRes,
    cardState: MissionCardState
) {
    val pairs = cardState.rewards
        .sortedBy {
            it.type.toInt()
        }
        .groupBy {
            it.type
        }.map { (type, rewards) ->
            type to rewards.sumOf { it.number }
        }.filter { (type, sum) ->
            sum != 0
        }

    val title = if (cardState.isMissionEnd) {
        stringRes.missionEventEnd
    } else {
        stringRes.missionCompleted
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PocketText(
            modifier = Modifier,
            config = PocketTextConfig(
                value = title,
                style = text17Sp(500),
                maxLines = 1
            )
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        PocketText(
            modifier = Modifier,
            config = PocketTextConfig(
                value = stringRes.missionCompletedSendBeforeDate.replaceArg(date),
                style = text15Sp()
            )
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        Row(verticalAlignment = Alignment.CenterVertically) {
            pairs.forEachIndexed { index, (type, sum) ->
                val state = when (type) {
                    CouponState.TYPE_DISCOUNT -> CouponState.DISCOUNT.copy(
                        unit = type.toCouponUnit()
                    )
                    CouponState.TYPE_RAFFLE -> CouponState.RAFFLE.copy(
                        unit = type.toCouponUnit()
                    )
                    CouponState.TYPE_EXCHANGE -> CouponState.EXCHANGE.copy(
                        unit = type.toCouponUnit()
                    )
                    else -> CouponState.DISCOUNT
                }
                CouponRewardShortCut(
                    couponState = state.copy(
                        number = sum.toString()
                    )
                )
                if (index != pairs.lastIndex) {
                    Spacer(modifier = Modifier.width(8.sdp()))
                }
            }
        }
    }
}

@Composable
private fun CouponRewardShortCut(couponState: CouponState) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.sdp()))
            .background(couponState.bgColor)
            .border(
                width = 1.sdp(),
                color = couponState.imageColor,
                shape = RoundedCornerShape(4.sdp())
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.sdp(), vertical = 4.sdp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.sdp()),
                painter = painterResource(id = couponState.imageId),
                contentDescription = "coupon_image"
            )
            Spacer(modifier = Modifier.width(4.sdp()))
            PocketText(
                config = PocketTextConfig(
                    value = "${couponState.number}${couponState.unit}",
                    textColor = couponState.imageColor,
                    style = text13Sp()
                )
            )
        }
    }
}
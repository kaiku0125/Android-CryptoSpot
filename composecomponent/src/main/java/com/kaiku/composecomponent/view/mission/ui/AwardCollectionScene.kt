package com.kaiku.composecomponent.view.mission.ui


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.mission.data.CouponState
import com.kaiku.composecomponent.view.mission.data.CouponState.Companion.TYPE_DISCOUNT
import com.kaiku.composecomponent.view.mission.data.CouponState.Companion.TYPE_EXCHANGE
import com.kaiku.composecomponent.view.mission.data.CouponState.Companion.TYPE_RAFFLE
import com.kaiku.composecomponent.view.mission.data.MissionStringRes
import com.kaiku.composecomponent.view.mission.data.MissionViewAction
import com.kaiku.composecomponent.view.mission.data.MissionViewState
import kotlin.math.roundToInt

@Composable
internal fun AwardCollectionScene(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    viewState: MissionViewState,
    action: (MissionViewAction) -> Unit
) {
    val list = viewState.couponList

    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .padding(
                    start = 16.sdp(),
                    end = 16.sdp(),
                    top = 8.sdp(),
                    bottom = 6.sdp()
                )
                .height(104.sdp()),
            shape = RoundedCornerShape(10.sdp()),
            colors = CardDefaults.cardColors(
                containerColor = color_333333
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                list.forEachIndexed { index, couponState ->
                    AwardItem(
                        modifier = Modifier
                            .padding(8.sdp())
                            .weight(1f),
                        stringRes = stringRes,
                        coupon = couponState,
                        onClick = {
                            action.invoke(
                                MissionViewAction.OnGotoMyCouponAction(
                                    couponType = couponState.type
                                )
                            )
                        }
                    )
                    if (index != list.lastIndex) {
                        VerticalDivider(
                            modifier = Modifier.padding(vertical = 10.sdp()),
                            color = color_414141
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .height(104.sdp())
                .padding(horizontal = 16.sdp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            list.forEachIndexed { index, couponState ->
                AnimationItem(
                    modifier = Modifier.weight(1f),
                    coupon = couponState,
                    onFinish = {
                        action.invoke(
                            MissionViewAction.AnimateCouponAction(
                                index = index,
                                runState = false
                            )
                        )
                    }
                )
            }
        }

    }
}

@Composable
private fun AnimationItem(
    modifier: Modifier = Modifier,
    coupon: CouponState,
    onFinish: () -> Unit
) {
    val animationState = coupon.aniState
    val isAniRunning = animationState.isAniRunning
    val animationTime = 1200
    val density = LocalDensity.current

    val alphaAnimation = animateFloatAsState(
        targetValue = if (isAniRunning) {
            0f
        } else {
            1f
        },
        animationSpec = tween(
            durationMillis = if (isAniRunning) {
                animationTime
            } else {
                0
            }
        ),
        label = ""
    )

    val offset by animateIntOffsetAsState(
        targetValue = if (isAniRunning) {
            IntOffset(
                x = 0,
                y = with(density) {
                    -(5.sdp()).toPx().roundToInt()
                }
            )
        } else {
            IntOffset(
                x = 0,
                y = with(density) {
                    25.sdp().toPx().roundToInt()
                }
            )
        },
        animationSpec = tween(
            if (isAniRunning) {
                animationTime
            } else {
                0
            }
        ),
        label = "offset",
        finishedListener = {
            onFinish.invoke()
        }
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (isAniRunning) {
            Row(
                modifier = Modifier
                    .padding(start = 30.sdp())
                    .align(Alignment.TopCenter)
                    .offset { offset }
                    .alpha(alphaAnimation.value),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PocketText(
                    config = PocketTextConfig(
                        value = "+${animationState.diff}",
                        style = text17Sp(500),
                        maxLines = 1
                    )
                )
                Image(
                    modifier = Modifier.size(13.sdp()),
                    painter = painterResource(id = coupon.imageId),
                    contentDescription = "looting_flag"
                )
            }
        }
    }
}

@Composable
private fun AwardItem(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes,
    coupon: CouponState,
    onClick: () -> Unit
) {
    val transferNumber = coupon.number.toIntOrNull().toNumberFormat(
        isShowThousandsSign = true,
        invalidText = "--"
    )

    val couponTitle = when (coupon.type) {
        TYPE_DISCOUNT -> stringRes.missionDiscount
        TYPE_RAFFLE -> stringRes.missionRaffle
        TYPE_EXCHANGE -> stringRes.missionExchange
        else -> "--"
    }

    val displayNumber = "${transferNumber}\t${coupon.unit}"
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.sdp()))
            .clickableEffectConfig(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(28.sdp()),
            painter = painterResource(id = coupon.imageId),
            contentDescription = "looting_flag"
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        AnimatedPocketText(
            config = PocketTextConfig(
                value = displayNumber,
                style = text17Sp(500),
                maxLines = 1
            )
        )
        Spacer(modifier = Modifier.height(4.sdp()))
        PocketText(
            config = PocketTextConfig(
                value = couponTitle,
                style = text13Sp(),
                maxLines = 1
            )
        )
    }
}

@Preview
@Composable
private fun AwardCollectionFieldPreview() {
    AwardCollectionScene(
        stringRes = MissionStringRes.Default,
        viewState = MissionViewState.PREVIEW,
        action = {

        }
    )
}